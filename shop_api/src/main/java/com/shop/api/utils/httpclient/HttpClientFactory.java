package com.shop.api.utils.httpclient;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:httpclient 4.3.x
 * </p>
 * 
 * @作者 Kayuu
 * @创建时间
 * @版本 1.00
 * @修改记录
 * 
 *       <pre>
 * 版本       修改人         修改时间         修改内容描述
 * ----------------------------------------
 * 
 * ----------------------------------------
 *       </pre>
 */
@SuppressWarnings("deprecation")
public class HttpClientFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientFactory.class);
	private static final String[] supportedProtocols = new String[] { "TLSv1" };

	public static CloseableHttpClient createHttpClient() {
		try {
			SSLContext sslContext = SSLContexts.custom().useSSL().build();
			SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return HttpClientBuilder.create().setSSLSocketFactory(sf).build();
		} catch (KeyManagementException e) {
			LOGGER.error("-->", e);
			;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("-->", e);
			;
		}
		return null;
	}

	public static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute) {
		try {
			SSLContext sslContext = SSLContexts.custom().useSSL().build();
			SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
			poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
			poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
			return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager)
					.setSSLSocketFactory(sf).build();
		} catch (KeyManagementException e) {
			LOGGER.error("-->", e);
			;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("-->", e);
			;
		}
		return null;
	}

	/**
	 * Key store 类型HttpClient
	 * 
	 * @param keystore
	 * @param keyPassword
	 * @return
	 */
	public static CloseableHttpClient createKeyMaterialHttpClient(KeyStore keystore, String keyPassword) {
		return createKeyMaterialHttpClient(keystore, keyPassword, supportedProtocols);
	}

	/**
	 * Key store 类型HttpClient
	 * 
	 * @param keystore
	 * @param keyPassword
	 * @param supportedProtocols
	 * @return
	 */
	public static CloseableHttpClient createKeyMaterialHttpClient(KeyStore keystore, String keyPassword,
			String[] supportedProtocols) {
		try {
			SSLContext sslContext = SSLContexts.custom().useSSL().loadKeyMaterial(keystore, keyPassword.toCharArray())
					.build();
			SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext, supportedProtocols, null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			return HttpClientBuilder.create().setSSLSocketFactory(sf).build();
		} catch (KeyManagementException e) {
			LOGGER.error("-->", e);
			;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("-->", e);
			;
		} catch (UnrecoverableKeyException e) {
			LOGGER.error("-->", e);
			;
		} catch (KeyStoreException e) {
			LOGGER.error("-->", e);
			;
		}
		return null;
	}

}
