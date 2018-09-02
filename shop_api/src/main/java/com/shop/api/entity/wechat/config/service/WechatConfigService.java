package com.shop.api.entity.wechat.config.service;

import com.shop.api.entity.wechat.config.WechatConfigEntity;

public interface WechatConfigService {
	
	/**
	 * 
	 * @Title: getWechatConfigEntityById   
	 * @Description: (根据Id获取微信公众号配置) 
	 * @author: 王杰    
	 * @date:  2018年7月25日 下午1:46:22   
	 * @param: @param Id
	 * @param: @return      
	 * @return: WechatConfigEntity      
	 * @throws
	 */
	WechatConfigEntity  getWechatConfigEntityById(long Id);
	
	/**
	 * 
	 * @Title: saveWechatConfigEntity   
	 * @Description: (新增微信公众号配置) 
	 * @author: 王杰    
	 * @date:  2018年7月25日 下午1:49:11   
	 * @param: @param wechatConfigEntity
	 * @param: @return      
	 * @return: WechatConfigEntity      
	 * @throws
	 */
	WechatConfigEntity saveWechatConfigEntity(WechatConfigEntity wechatConfigEntity);
	
	/**
	 * 
	 * @Title: getWechatConfigEntityByCacheRedis   
	 * @Description: (从缓存里面获取微信公众号配置) 
	 * @author: 王杰    
	 * @date:  2018年7月26日 下午5:51:05   
	 * @param: @param code
	 * @param: @return      
	 * @return: WechatConfigEntity      
	 * @throws
	 */
	WechatConfigEntity  getWechatConfigEntityByCacheRedis(String code);
	
	/**
	 * 
	 * @Title: getWechatConfigEntityByCode   
	 * @Description: (根据code获取微信公众号配置)   
	 * @param code
	 * @return      
	 * @return WechatConfigEntity  
	 * @author 王杰
	 * @date 2018年7月27日 下午4:54:08   
	 * @throws
	 */
	WechatConfigEntity  getWechatConfigEntityByCode(String code);
}
