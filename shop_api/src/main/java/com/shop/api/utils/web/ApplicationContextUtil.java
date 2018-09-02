package com.shop.api.utils.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @ClassName  ApplicationContextUtil   
 * @Description (通过上下文获取Bean)   
 * @author: 王杰
 * @date:   2018年7月27日 下午3:30:02   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class ApplicationContextUtil implements ApplicationContextAware
{
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException 
	{
		ApplicationContextUtil.context = context;
	}
	
	/**
	 * 通过Spring配置文件的Bean对应的ID获取对象示例
	 * @author majun 
	 * @date 2014-9-28
	 * @param 	beanId			Spring里配置文件中的Bean对应的ID
	 * @param	clazz			对象Class字节码
	 * @return	T				对象示例
	 */
	public static <T> T getBean(String beanId, Class<T> clazz)
	{
		return context.getBean(beanId, clazz);
	}
	
}
