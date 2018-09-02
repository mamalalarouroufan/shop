package com.shop.api.entity.user.service;

import com.shop.api.entity.user.UserEntity;

public interface UserEntityService {

	/**
	 * 
	 * @Title: getUserEntityByPhone   
	 * @Description: (根据手机号来获取用户实体) 
	 * @author: 王杰    
	 * @date:  2018年7月25日 上午1:59:53   
	 * @param: @param phone
	 * @param: @return      
	 * @return: UserEntity      
	 * @throws
	 */
	UserEntity getUserEntityByPhone(String phone);
	
	/**
	 * 
	 * @Title: getUserEntityById   
	 * @Description: (根据用户主键来获取用户实体) 
	 * @author: 王杰    
	 * @date:  2018年7月25日 上午2:00:31   
	 * @param: @param Id
	 * @param: @return      
	 * @return: UserEntity      
	 * @throws
	 */
	UserEntity getUserEntityById(String Id);
	
	/**
	 * 
	 * @Title: saveUserEntity   
	 * @Description: 新增一条用户信息
	 * @author: 王杰    
	 * @date:  2018年7月25日 上午2:01:26   
	 * @param: @param userEntity
	 * @param: @return      
	 * @return: UserEntity      
	 * @throws
	 */
	UserEntity saveUserEntity(UserEntity userEntity);
	
}
