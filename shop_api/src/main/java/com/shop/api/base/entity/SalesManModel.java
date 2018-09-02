package com.shop.api.base.entity;

import java.io.Serializable;

public class SalesManModel implements Serializable {
	
	private static final long serialVersionUID = 1501259502828575043L;
	
	/** 用户id */
	private String userId;
	
	/**  用户账号 */
	private String userName;

	/** 用户名称 */
	private String cName;

	/** 消费来源 */
	private String channel;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
