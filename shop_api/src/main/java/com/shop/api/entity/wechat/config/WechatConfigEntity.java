package com.shop.api.entity.wechat.config;

import com.shop.api.base.entity.BaseObject;

/**
 * 
 * @ClassName:  WechatConfigEntity   
 * @Description:(微信公众号配置实体类(s_wechat_config_t))   
 * @author: 王杰
 * @date:   2018年7月25日 上午1:28:05   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class WechatConfigEntity extends BaseObject{

	private static final long serialVersionUID = 6904111225723656023L;
	
	/** 微信公众号编码 */
	private String wechatCode;

	/** 微信公众号开发者ID(AppID) */
	private String appId;
	
	/** 微信公众号开发者密码(AppSecret) */
	private String appSecret;
	
	/** 微信公众号Token */
	private String token;
	
	/** 微信公众号服务器地址 */
	private String serviceUrl;
	
	/** 微信网页授权回调地址 */
	private String callbackUrl;
	
	/** 微信公众号ID(例如:gh_0fd5a458bfc8) */
	private String wechatId;
	
	/** 微信公众号(例如:xiaxiangdian_kf) */
	private String wechatNum;
	
	/** 微信公众号名称(例如:霞湘店) */
	private String wechatName;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatNum() {
		return wechatNum;
	}

	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getWechatCode() {
		return wechatCode;
	}

	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}
	
	
}
