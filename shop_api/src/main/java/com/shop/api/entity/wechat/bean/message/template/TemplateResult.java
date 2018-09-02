package com.shop.api.entity.wechat.bean.message.template;

import com.shop.api.entity.wechat.base.BaseResult;

/**
 * 
 * @ClassName  TemplateResult   
 * @Description (模板信息返回实体)   
 * @author: 王杰
 * @date:   2018年8月10日 下午1:29:30   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class TemplateResult extends BaseResult{

	/** 消息Id */
	private String msgid;

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
}
