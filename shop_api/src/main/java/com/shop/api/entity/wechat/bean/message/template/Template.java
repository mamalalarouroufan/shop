package com.shop.api.entity.wechat.bean.message.template;

/**
 * 
 * @ClassName  Template   
 * @Description (微信通知模板消息)   
 * @author: 王杰
 * @date:   2018年8月10日 下午1:20:38   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class Template {

	/** 接收消息的openId */
	private String touser;
	
	/** 模板Id */
	private String template_id;
	
	/** 点击后指向url */
	private String url;
	
	/** 跳小程序所需数据，不需跳小程序可不用传该数据 */
	private Miniprogram miniprogram;
	
	/** 消息内容 */
	private Data data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Miniprogram getMiniprogram() {
		return miniprogram;
	}

	public void setMiniprogram(Miniprogram miniprogram) {
		this.miniprogram = miniprogram;
	}
	
}
