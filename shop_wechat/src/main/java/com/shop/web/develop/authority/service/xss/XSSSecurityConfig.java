package com.shop.web.develop.authority.service.xss;

/**
 * 
 * 安全过滤配置信息类 
 * @Copyright 北京瑞友科技股份有限公司
 * @author xiaolei
 * @date 2014年9月28日
 * =================Modify Record=================
 * @Modifier			@date			@Content
 *
 */
public class XSSSecurityConfig {
	  
	/**
	 * CHECK_HEADER：是否开启header校验
	 */
	public static boolean IS_CHECK_HEADER; 
	
	/**
	 * CHECK_PARAMETER：是否开启parameter校验
	 */
	public static boolean IS_CHECK_PARAMETER;

    /**
     * CHECK_URL,是否开启检查特殊url
     */
    public static boolean IS_CHECK_URL;

	/**
	 * IS_LOG：是否记录日志
	 */
	public static boolean IS_LOG;
	
	/**
	 * IS_LOG：是否中断操作
	 */
	public static boolean IS_CHAIN;
	
	/**
	 * REPLACE：替换模式   1：替换    2：转码   0：不作处理 
	 */
	public static String REPLACE;
	
	/**
	 * INGORE_URL：不进行校验的url
	 */
	public static String INGORE_URL;
}
