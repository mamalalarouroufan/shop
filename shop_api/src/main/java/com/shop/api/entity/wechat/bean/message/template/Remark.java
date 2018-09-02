package com.shop.api.entity.wechat.bean.message.template;
/**
 * 
 * @ClassName  Remark   
 * @Description (底部信息)   
 * @author: 王杰
 * @date:   2018年8月10日 下午1:26:15   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class Remark {
	

	public Remark() {
	}

	public Remark(String value, String color) {
		super();
		this.value = value;
		this.color = color;
	}

	/** 消息 */
	private String value;
	
	/** 颜色 */
	private String color;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
