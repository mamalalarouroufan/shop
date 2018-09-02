package com.shop.api.utils.UUID;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 主键生成工具类
 * @Description: 
 * @author zhangzubing
 * @date 2016年3月21日 下午12:17:00
 * @version 1.0
 */
public class UUIDUtil {

	/*
	 * UUID(Universally Unique Identifier)全局唯一标识符,是指在一台机器上生成的数字；
	 * 它保证对在同一时空中的所有机器都是唯一的。
	 * 按照开放软件基金会(OSF)制定的标准计算，
	 * 用到了以太网卡地址、纳秒级时间、芯片ID码和许多可能的数字。
	 */

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static StringBuffer buf = new StringBuffer();
	
	private static Lock lock = new ReentrantLock();// 锁对象
	

	/**
	 * 随机产生32位16进制字符串
	 * @Description: 
	 * @author zhangzubing
	 * @date 2016年3月21日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getRandom32PK() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 随机产生19长度位字符串，以时间开头
	 * @Description: 
	 * @author zhangzubing
	 * @date 2016年3月21日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getRandomBeginTimePK() {
		lock.lock(); //加锁
		Date d = new Date();
		String timeStr = sdf.format(d);
		
		String random32 = getRandom32PK();
		Random random = new Random();
		buf.setLength(0);
		int n;
		for(int i = 0; i < 2; i++){
			n =  random.nextInt(random32.length()) -1;
			if(n < 0){
				n = 0;
			}
			buf.append(random32.substring(n, n+1));
		}
		
		lock.unlock();
		
		return timeStr + buf.toString();
	}

	/**
	 * 随机产生32位16进制字符串，以时间开头
	 * @Description: 
	 * @author zhangzubing
	 * @date 2016年3月21日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getRandom32BeginTimePK() {
		lock.lock(); //加锁
		Date d = new Date();
		String timeStr = sdf.format(d);
		String random32 = getRandom32PK();
		lock.unlock();
		return timeStr + random32.substring(17, random32.length());
	}

	/**
	 * 随机产生32位16进制字符串，以时间结尾
	 * @Description: 
	 * @author zhangzubing
	 * @date 2016年3月21日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getRandom32EndTimePK() {
		lock.lock(); //加锁
		Date d = new Date();
		String timeStr = sdf.format(d);
		String random32 = getRandom32PK();
		lock.unlock();
		return random32.substring(0, random32.length() - 17) + timeStr;
	}
	
	
	public final static String generateWithSeq() {
		return Long.toString(System.currentTimeMillis() * 1000000
				+ new AtomicLong(1).incrementAndGet());
	}
	
	/**
	 * 获取随机字符串 a-z,A-Z,0-9
	 * @param length	长度
	 * @return
	 */
	public static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; ++i) {
			sb.append(str.charAt(random.nextInt(36)));
		}
		return sb.toString();
	}
}
