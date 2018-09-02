package com.shop.api.entity.user;

import com.shop.api.base.entity.BaseObject;
/**
 * 
 * @ClassName:  UserWechatMergeEntity   
 * @Description:(用户微信关联关系表实体类(s_user_wechat_merge_t))   
 * @author: 王杰
 * @date:   2018年7月25日 上午1:54:49   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class UserWechatMergeEntity extends BaseObject{

	private static final long serialVersionUID = -2042355529020056059L;

	/** 用户表主键 */
	private Long userId;
	
	/** 微信公众号用户唯一ID */
	private String openId;
	
	/** 所属公众号 */
	private String appId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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
	
}
