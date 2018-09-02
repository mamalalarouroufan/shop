package com.shop.api.entity.wechat.message.service;

import com.shop.api.entity.wechat.bean.message.template.Template;

/**
 * 
 * @ClassName  TemplateMessageService   
 * @Description (微信模板消息发送service)   
 * @author: 王杰
 * @date:   2018年8月10日 上午11:48:41   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public interface TemplateMessageService {

	/**
	 * 
	 * @Title: setUpIndustry   
	 * @Description: (设置所属行业)   
	 * @param code      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月10日 上午11:58:25   
	 * @throws
	 */
	void setUpIndustry(String code);

	/**
	 * 
	 * @Title: getIndustryInformation   
	 * @Description: (获取设置的行业信息)   
	 * @param code
	 * @return      
	 * @return String  
	 * @author 王杰
	 * @date 2018年8月10日 上午11:58:30   
	 * @throws
	 */
	String getIndustryInformation(String code);

	/**
	 * 
	 * @Title: getTemplateId   
	 * @Description: (获得模板ID)   
	 * @param code
	 * @return      
	 * @return String  
	 * @author 王杰
	 * @date 2018年8月10日 上午11:59:41   
	 * @throws
	 */
	String getTemplateId(String code);

	/**
	 * 
	 * @Title: getAllPrivateTemplate   
	 * @Description: (获取已添加至帐号下所有模板列表)   
	 * @param code
	 * @return      
	 * @return String  
	 * @author 王杰
	 * @date 2018年8月10日 下午12:04:24   
	 * @throws
	 */
	String getAllPrivateTemplate(String code);

	/**
	 * 
	 * @Title: delPrivateTemplate   
	 * @Description: (删除模板)   
	 * @param code
	 * @param templateId      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月10日 下午12:05:55   
	 * @throws
	 */
	void delPrivateTemplate(String code ,String templateId);
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: sendTemplateMessage   
	 * @Description: (微信发送模板消息)   
	 * @param code
	 * @param template      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月10日 下午2:39:24   
	 * @throws
	 */
	void sendTemplateMessage(String code,Template template) throws Exception;
}
