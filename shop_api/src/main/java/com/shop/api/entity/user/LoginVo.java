package com.shop.api.entity.user;

/**
 * 
 * @ClassName  LoginVo   
 * @Description (保存登陆信息)   
 * @author: 王杰
 * @date:   2018年7月27日 下午3:41:43   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class LoginVo extends UserEntity {

	private static final long serialVersionUID = 2459760717083080671L;

	/** 用户访问当前公众号唯一标识 */
	private String openId;

	/** 公众号开发者Id */
	private String appId;

	/** 公众号自定义编号 */
	private String wechatCode;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getWechatCode() {
		return wechatCode;
	}

	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}

}
