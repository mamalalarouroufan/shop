package com.shop.service.key;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.shop.api.entity.key.Key;
import com.shop.api.entity.key.service.KeyService;

/**
 * 生成主键：在项目启动时，将库里的主键id全部查询出来，放到本地内存中(集合)。 <br/>
 * 主键实例：2014 0001 0001 0000 001 <br/>
 * 1-4位：机器码(应用服务器) 5-8位：表代码 code 9-19位：增长id
 * 
 */
public class FactoryAboutKey {
	private static final Logger log = LoggerFactory.getLogger(FactoryAboutKey.class);

	public static ConcurrentHashMap<String, String> keyFinalList = new ConcurrentHashMap<String, String>();
	private static KeyService keyService;
	private static List<Key> keys = new ArrayList<Key>();
	// 从配置文件读取的value对象
	private static Map<String, String> valueReadFromProp = new HashMap<String, String>();

	/** 机器码 */
	private static String machineCode;

	/**
	 * 初始化 <br/>
	 * 1.从配置文件读取表名和代码的键值对,防御 <br/>
	 * 2.获取机器码 <br/>
	 * 3.用 （机器码、表代码、11位字符串）组装主键
	 */
	public void init() throws Exception {
		// 从配置文件读取的key对象
		Map<String, Key> keyReadFromProp = new HashMap<String, Key>();
		Key key = null;
		Properties prop = null;
		// 从配置文件读取的key对象
		List<String> listForTblExist = new ArrayList<String>();

		// 获取表名所对应的代码
		prop = PropertiesLoaderUtils.loadAllProperties("properties/key.properties");
		Enumeration<?> en = prop.propertyNames(); // 得到配置文件的名字
		while (en.hasMoreElements()) {
			key = new Key();
			String strKey = (String) en.nextElement();
			String strValue = prop.getProperty(strKey);
			key.setTblName(strKey);
			key.setMachineCode(machineCode);
			keyReadFromProp.put(strKey, key);
			listForTblExist.add(strKey);
			valueReadFromProp.put(strKey, strValue);
		}

		// 判断表名是否在数据库中存在
		Map<String, String> keyMap = new HashMap<String, String>();
		List<Key> tblNamelistFromSuorce = keyService.queryTblList();
		for (Key k : tblNamelistFromSuorce) {
			keyMap.put(k.getTblName(), k.getId());
		}
		Key _key = null;

		for (String tableName : listForTblExist) {
			if (!keyMap.containsKey(tableName)) {
				throw new Exception("表名在数据库中不存在或key.properties配置文件中没有做相应的配置:" + tableName);
			}
			_key = keyReadFromProp.get(tableName);
			_key.setId(keyMap.get(tableName));
			keys.add(_key);
		}

		// 拼装主键id
		List<Key> keyQueryFromDatasource = keyService.queryKeyList(keys);
		if (log.isDebugEnabled()) {
			log.debug("## keyQueryFromDatasource.size={}", keyQueryFromDatasource.size());
		}
		for (Iterator<Key> iterator = keyQueryFromDatasource.iterator(); iterator.hasNext();) {
			Key k = iterator.next();
			if (StringUtils.isEmpty(k.getId())) {
				String newId = machineCode + valueReadFromProp.get(k.getTblName().toUpperCase()) + "00000000000";
				keyFinalList.put(k.getTblName(), newId);
			} else if (StringUtils.isNotEmpty(k.getId()) && k.getId().length() < 17) {
				String newId = machineCode + k.getId();
				if (newId.length() > 17) {
					newId = newId.substring(0, 17);
				} else {
					int length = newId.length();
					for (int i = length; i < 17; i++) {
						newId = newId + "0";
					}
				}
				keyFinalList.put(k.getTblName(), newId);
			} else {
				keyFinalList.put(k.getTblName(), k.getId());
			}
		}

	}

	private static Lock lock = new ReentrantLock();// 锁对象

	/**
	 * 根据表名从本地内存读取所对应的id
	 */
	public static String getKeyByTblName(String tblName) {
		boolean isloap = true;
		String finalId = "";
		while (isloap) {
			lock.lock();
			String code = keyFinalList.get(tblName.toLowerCase());
			if (StringUtils.isEmpty(code)) {
				log.error("## 表={} ， 配置文件中(key.properties)不存在相对应的键值对!", tblName);
			} else {
				String machineCode = code.substring(0, 4);
				String keyValue = code.substring(4, 8);
				int subLength = code.length() > 17 ? code.length() - 17 : 0;
				String idValue = "";
				if (subLength > 0) {
					idValue = code.substring(8 + subLength, code.length());
				} else {
					idValue = code.substring(8, code.length());
				}

				long id = Long.valueOf(idValue);
				id++;
				int idLenth = String.valueOf(id).length();

				String containZero = "";
				for (int i = 0; i < idValue.length() - idLenth; i++) {
					containZero += "0";
				}
				containZero += String.valueOf(id);
				finalId = machineCode + keyValue + containZero;
				keyFinalList.replace(tblName.toLowerCase(), finalId);
			}

			isloap = false;
			lock.unlock();
		}
		return finalId;
	}

	public static Long getKeyByTblNameTypeLong(String tblName) {
		return Long.valueOf(getKeyByTblName(tblName));
	}

	public KeyService getKeyService() {
		return keyService;
	}

	@SuppressWarnings("static-access")
	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}

	public static String getMachineCode() {
		return machineCode;
	}

	@SuppressWarnings("static-access")
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

}
