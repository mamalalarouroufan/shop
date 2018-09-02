package com.shop.api.entity.wechat.message.service;
/**
 * 
 * @ClassName  CustomerMessageService   
 * @Description (客服消息service)   
 * @author: 王杰
 * @date:   2018年8月10日 上午11:46:40   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public interface CustomerMessageService {

	/**
	 * 
	 * @Title: sendCustomerMessage   
	 * @Description: (发送客服消息)   
	 * @param Id
	 * @param openId
	 * @param type      
	 * @return: void  
	 * @author 王杰
	 * @date 2018年7月27日    
	 * @throws
	 */
	void sendCustomerMessage(String code, String openId, String type);
}
