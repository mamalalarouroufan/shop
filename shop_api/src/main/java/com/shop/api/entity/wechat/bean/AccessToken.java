package com.shop.api.entity.wechat.bean;

import java.io.Serializable;

import com.shop.api.entity.wechat.base.BaseResult;

/**
 * 微信通用接口凭证
 */
public class AccessToken extends BaseResult implements Serializable{

	private static final long serialVersionUID = 3077156563111789938L;
	
	// access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token
	private String access_token;
	
	// 凭证有效时间，单位：秒
	private Long expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

}
