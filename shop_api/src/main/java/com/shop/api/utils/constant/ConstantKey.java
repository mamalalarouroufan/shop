package com.shop.api.utils.constant;

/**
 * 
 * @ClassName  CookieKeyConstant   
 * @Description (Cookie设置中的常量类)   
 * @author: 王杰
 * @date:   2018年7月27日 下午3:17:25   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class ConstantKey {

	/**
	 * cookie 有效期
	 */
	// 后台有效期
	public final static int COOKIE_MAX_AGE_ADMIN = 60 * 60 * 24;
	
	// 前台有效期
	public final static int COOKIE_MAX_AGE = 60 * 60 * 2;
	
	/**
	 * 微信token
	 */
	/** cookie 微信token */
	public final static String COOKIE_TOKEN_WX_KEY = "token_wx";
	
	/** cookie 微信wechatCode */
	public final static String COOKIE_WECHATCODE_KEY = "wechatCode";
	
	/** 微信token时间戳 */
	public final static String COOKIE_TOKEN_TIMESTAMP_KEY = "_t";
	
	/** 微信token随机码 */
	public final static String COOKIE_TOKEN_RANDOM_KEY = "_r";
	
	/** 微信配置实体存储在redis中key名称 */
	public static final String WECHAT_CONFIG_ENTITY_KEY = "wechat:config:entity:%s";

	/** 微信公众号中拉去用户信息存放在redis中key名称 */
	public static final String WECHAT_APPID_USERWECHAT_KEY = "wechat:code:%s:userwechat:%s";
	
	/** 微信公众号全局唯一接口调用凭据存储在redis中key名称 */
	public static final String WECHAT_APPID_ACCESSTOKEN_KEY = "wechat:code:%s:accesstoken";
	
	/** 保存在redis中微信端用户登陆信息key */
	public static String WECHAT_LOGIN_KEY="wechat:code:%s:login:user:%s";
	
	/**
	 * 字典项redis保存key
	 */
	
	/** 字典项 */
	public static String DICT_KEY="dict:";
	/** 电话区域 */
	public static String DICT_PHONE_AREA_KEY="PHONE_AREA";
	/** 证件类型 */
	public static String DICT_CERT_TYPE_KEY="CERT_TYPE";
	/** 快递公司 */
	public static String DICT_EXPRESS_COMPANY_KEY="EXPRESS_COMPANY";
	/** 电话区域 */
	public static String DICT_EDUCATION_LEVEL_KEY="EDUCATION_LEVEL";
	
	
}
