package com.shop.service.wechat.WechatConfig.mapper;

import com.shop.api.entity.wechat.config.WechatConfigEntity;
/**
 * 
 * @ClassName:  WechatConfigMapper   
 * @Description:(微信公众号配置Mapper)   
 * @author: 王杰
 * @date:   2018年7月25日 下午1:57:15   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public interface WechatConfigMapper {

	/**
	 * 
	 * @Title: getWechatConfigEntityById   
	 * @Description: (根据Id获取微信公众号配置) 
	 * @author: 王杰    
	 * @date:  2018年7月25日 下午1:47:23   
	 * @param: @param id
	 * @param: @return      
	 * @return: WechatConfigEntity      
	 * @throws
	 */
	WechatConfigEntity getWechatConfigEntityById(long id);
	
	/**
	 * 
	 * @Title: saveWechatConfigEntity   
	 * @Description: (新增微信公众号配置) 
	 * @author: 王杰    
	 * @date:  2018年7月25日 下午1:56:52   
	 * @param: @param wechatConfigEntity
	 * @param: @return      
	 * @return: Integer      
	 * @throws
	 */
	Integer saveWechatConfigEntity(WechatConfigEntity wechatConfigEntity);

	/**
	 * 
	 * @Title: getWechatConfigEntityByCode   
	 * @Description: (根据code获取微信公众号配置)   
	 * @param code
	 * @return      
	 * @return WechatConfigEntity  
	 * @author 王杰
	 * @date 2018年7月27日 下午4:55:04   
	 * @throws
	 */
	WechatConfigEntity getWechatConfigEntityByCode(String code);

}
