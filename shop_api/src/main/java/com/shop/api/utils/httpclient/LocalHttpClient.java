package com.shop.api.utils.httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shop.api.utils.UUID.UUIDUtil;

public class LocalHttpClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(LocalHttpClient.class);
	protected static CloseableHttpClient httpClient = HttpClientFactory.createHttpClient(100, 10);

	private static ConcurrentHashMap<String, CloseableHttpClient> httpClient_mchKeyStore = new ConcurrentHashMap<String, CloseableHttpClient>();

	public static void init(int maxTotal, int maxPerRoute) {
		try {
			httpClient.close();
		} catch (IOException e) {
			LOGGER.error("-->", e);
			
		}
		httpClient = HttpClientFactory.createHttpClient(maxTotal, maxPerRoute);
	}

	/**
	 * 初始化   MCH HttpClient KeyStore
	 * @param mch_id
	 * @param keyStoreFilePath
	 */
	public static void initMchKeyStore(String mch_id, String keyStoreFilePath) {
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(keyStoreFilePath));
			keyStore.load(instream, mch_id.toCharArray());
			instream.close();
			CloseableHttpClient httpClient = HttpClientFactory.createKeyMaterialHttpClient(keyStore, mch_id);
			httpClient_mchKeyStore.put(mch_id, httpClient);
		} catch (KeyStoreException e) {
			LOGGER.error("-->", e);

		} catch (FileNotFoundException e) {
			LOGGER.error("-->", e);

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("-->", e);

		} catch (CertificateException e) {
			LOGGER.error("-->", e);

		} catch (IOException e) {
			LOGGER.error("-->", e);

		}
	}

	public static CloseableHttpResponse execute(HttpUriRequest request) throws Exception {
		return httpClient.execute(request, HttpClientContext.create());
	}

	public static <T> T execute(HttpUriRequest request, ResponseHandler<T> responseHandler) throws Exception {
		return httpClient.execute(request, responseHandler, HttpClientContext.create());
	}

	/**
	 * 数据返回自动JSON对象解析
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception 
	 */
	public static <T> T executeJsonResult(HttpUriRequest request, Class<T> clazz) throws Exception {
		return execute(request, JsonResponseHandler.createResponseHandler(clazz));
	}

	/**
	 * 数据返回自动XML对象解析
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception 
	 */
	public static <T> T executeXmlResult(HttpUriRequest request, Class<T> clazz) throws Exception {
		return execute(request, XmlResponseHandler.createResponseHandler(clazz));
	}

	/**
	 * MCH keyStore 请求 数据返回自动XML对象解析
	 * @param mch_id
	 * @param request
	 * @param clazz
	 * @return
	 */
	public static <T> T keyStoreExecuteXmlResult(String mch_id, HttpUriRequest request, Class<T> clazz) {
		try {
			return httpClient_mchKeyStore.get(mch_id).execute(request, XmlResponseHandler.createResponseHandler(clazz),
					HttpClientContext.create());
		} catch (ClientProtocolException e) {
			LOGGER.error("-->", e);
			
		} catch (IOException e) {
			LOGGER.error("-->", e);
			
		}
		return null;
	}

	/**
	 * 
	 * @Title: executeDownloadFile   
	 * @Description: (下载文件)   
	 * @param httpGet
	 * @param path 保存路径(例如:D://image或者..//image)
	 * @param fileName 文件名称不要带类型
	 * @throws Exception      
	 * @return void  
	 * @author 王杰
	 * @date 2018年7月27日 下午6:32:12   
	 * @throws
	 */
	public static String executeDownloadFile(HttpGet httpGet, String path, String fileName) throws Exception {
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);

		HttpEntity httpEntity = closeableHttpResponse.getEntity(); // 获取实体

		if (httpEntity != null) {
			MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
			MimeType jpeg = allTypes.forName(httpEntity.getContentType().getValue());
			String ext = jpeg.getExtension();
			if (StringUtils.isBlank(ext)) {
				String[] s = httpEntity.getContentType().getValue().split("/");
				ext = "." + s[s.length - 1];
			}
			if (fileName == null) {
				fileName = UUIDUtil.getRandom32PK();
			}
			InputStream inputStream = httpEntity.getContent();
			File file = new File(path + "//" + fileName + ext);
			FileUtils.copyInputStreamToFile(inputStream, file); // 将图片保存在本次磁盘
			LOGGER.info("文件保存成功 --> 路径{}", file.getAbsolutePath());
			return file.getAbsolutePath();
		}
		return null;
	}
}
