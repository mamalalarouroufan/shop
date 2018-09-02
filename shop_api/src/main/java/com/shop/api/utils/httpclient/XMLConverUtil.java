package com.shop.api.utils.httpclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

/**
 * 
 * 
 * <p>Title:</p>
 * <p>Description:XML 数据接收对象转换工具类 </p>
 * @作者 Kayuu
 * @创建时间
 * @版本 1.00
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * ----------------------------------------
 * 
 * ----------------------------------------
 * </pre>
 */
public class XMLConverUtil{
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLConverUtil.class);
	private static final ThreadLocal<Map<Class<?>,Marshaller>> mMapLocal = new ThreadLocal<Map<Class<?>,Marshaller>>() {
		@Override
		protected Map<Class<?>, Marshaller> initialValue() {
			return new HashMap<Class<?>, Marshaller>();
		}
	};

	private static final ThreadLocal<Map<Class<?>,Unmarshaller>> uMapLocal = new ThreadLocal<Map<Class<?>,Unmarshaller>>(){
		@Override
		protected Map<Class<?>, Unmarshaller> initialValue() {
			return new HashMap<Class<?>, Unmarshaller>();
		}
	};

	/**
	 * XML to Object
	 * @param <T>
	 * @param clazz
	 * @param xml
	 * @return
	 */
	public static <T> T convertToObject(Class<T> clazz,String xml){
		return convertToObject(clazz,new StringReader(xml));
	}

	/**
	 * XML to Object
	 * @param <T>
	 * @param clazz
	 * @param inputStream
	 * @return
	 */
	public static <T> T convertToObject(Class<T> clazz,InputStream inputStream){
		return convertToObject(clazz,new InputStreamReader(inputStream));
	}

	/**
	 * XML to Object
	 * @param <T>
	 * @param clazz
	 * @param reader
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToObject(Class<T> clazz,Reader reader){
		try {
			Map<Class<?>, Unmarshaller> uMap = uMapLocal.get();
			if(!uMap.containsKey(clazz)){
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				uMap.put(clazz, unmarshaller);
			}
			return (T) uMap.get(clazz).unmarshal(reader);
		} catch (JAXBException e) {
			LOGGER.error("-->",e);;
		}
		return null;
	}

	/**
	 * Object to XML
	 * @param object
	 * @return
	 */
	public static String convertToXML(Object object){
		try {
			Map<Class<?>, Marshaller> mMap = mMapLocal.get();
			if(!mMap.containsKey(object.getClass())){
				JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	            marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
	                public void escape(char[] ac, int i, int j, boolean flag,Writer writer) throws IOException {
	                writer.write( ac, i, j ); }
	            });
				mMap.put(object.getClass(), marshaller);
			}
			StringWriter stringWriter = new StringWriter();
			mMap.get(object.getClass()).marshal(object,stringWriter);
			return stringWriter.getBuffer().toString();
		} catch (JAXBException e) {
			LOGGER.error("-->",e);;
		}
		return null;
	}

	/**
	 * 转换简单的xml to map
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")  
	public static Map<String,String> convertToMap(String xml){
		Map<String, String> map = new LinkedHashMap<String, String>();
	
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));

			SAXReader reader = new SAXReader();  
			Document document = reader.read(inputStream);  
		    // 得到xml根元素  
		    Element root = document.getRootElement();  
		    // 得到根元素的所有子节点  
			    List<Element> elementList = root.elements();  
  
			    // 遍历所有子节点  
		    for (Element e : elementList){
		    	map.put(e.getName(), e.getText());  
		    }			    
		    // 释放资源  
		    inputStream.close();  
		    inputStream = null;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("-->",e);;
		} catch (DocumentException e) {
			LOGGER.error("-->",e);;
		} catch (IOException e) {
			LOGGER.error("-->",e);;
		}  	
		return map;
	}
}
