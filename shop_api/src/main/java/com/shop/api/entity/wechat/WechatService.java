package com.shop.api.entity.wechat;

import com.shop.api.entity.wechat.bean.AccessToken;
import com.shop.api.entity.wechat.bean.UserWechat;
import com.shop.api.entity.wechat.bean.WechatQRCode;

public interface WechatService {

	/**
	 * 
	 * @Title: getAccessToken   
	 * @Description: (获取公众号的全局唯一接口调用凭据)   
	 * @param Id
	 * @return      
	 * @return AccessToken  
	 * @author 王杰
	 * @date 2018年7月27日 下午12:28:22   
	 * @throws
	 */
	AccessToken getAccessToken(String code);

	/**
	 * 
	 * @Title: getAccessTokenByCacheRedis   
	 * @Description: (从缓存中获取公众号的全局唯一接口调用凭据)   
	 * @param Id
	 * @return      
	 * @return AccessToken  
	 * @author 王杰
	 * @date 2018年7月27日 下午12:28:13   
	 * @throws
	 */
	AccessToken getAccessTokenByCacheRedis(String code);

	/**
	 * 
	 * @Title: getUserWechat   
	 * @Description: (微信公众号获取用户基本信息（包括UnionID机制）)   
	 * @param Id
	 * @param openid
	 * @return      
	 * @return UserWechat  
	 * @author 王杰
	 * @date 2018年7月27日 下午12:22:19   
	 * @throws
	 */
	UserWechat getUserWechat(String code, String openid);

	/**
	 * 
	 * @Title: getUserWechatByCacheRedis   
	 * @Description: (从缓存中获取微信公众号用户基本信息（包括UnionID机制）)   
	 * @param Id
	 * @param openid
	 * @return      
	 * @return: UserWechat  
	 * @author 王杰
	 * @date 2018年7月27日    
	 * @throws
	 */
	UserWechat getUserWechatByCacheRedis(String code, String openid);

	/** 
	 * 
	 * @Title: createTempTicket   
	 * @Description: (创建临时带参数二维码(数字))   
	 * @param Id
	 * @param expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒
	 * @param sceneId  场景Id
	 * @return      
	 * @return WechatQRCode  
	 * @author 王杰
	 * @date 2018年7月27日 下午12:27:13   
	 * @throws
	 */
	WechatQRCode createTempTicket(String code, String expireSeconds, int sceneId);
	
	/**
	 * 
	 * @Title: createTempTicket   
	 * @Description: (创建临时带参数二维码(字符串))   
	 * @param code
	 * @param expireSeconds
	 * @param sceneStr
	 * @return      
	 * @return WechatQRCode  
	 * @author 王杰
	 * @date 2018年7月27日 下午5:16:59   
	 * @throws
	 */
	WechatQRCode createTempTicket(String code, String expireSeconds, String sceneStr);
	
	/**
	 * 
	 * @Title: createForeverTicket   
	 * @Description: (创建永久二维码(数字))   
	 * @param Id
	 * @param sceneId 场景Id
	 * @return      
	 * @return WechatQRCode  
	 * @author 王杰
	 * @date 2018年7月27日 下午12:37:47   
	 * @throws
	 */
	WechatQRCode createForeverTicket(String code, int sceneId);
	
	/**
	 * 
	 * @Title: createForeverStrTicket   
	 * @Description: (创建永久二维码(字符串))   
	 * @param Id
	 * @param sceneStr
	 * @return      
	 * @return WechatQRCode  
	 * @author 王杰
	 * @date 2018年7月27日 下午12:38:38   
	 * @throws
	 */
	WechatQRCode createForeverSttrTicket(String code, String sceneStr);
}
