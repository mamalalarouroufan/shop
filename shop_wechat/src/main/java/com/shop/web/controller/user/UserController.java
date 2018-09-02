package com.shop.web.controller.user;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.api.base.controller.BaseController;
import com.shop.api.base.entity.WebEntity;
import com.shop.api.entity.user.UserEntity;
import com.shop.api.entity.user.service.UserEntityService;
import com.shop.api.exception.EnumError;
import com.shop.web.controller.wechat.WechatController;
/**
 * 
 * @ClassName:  UserController   
 * @Description:(用户Controller)   
 * @author: 王杰
 * @date:   2018年7月25日 下午2:02:49   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
@Controller
public class UserController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);
	
	@Resource
	private UserEntityService userEntityService;
	
	/**
	 * 
	 * @Title: getUserByPhone   
	 * @Description: (根据用户手机号来获取用户) 
	 * @author: 王杰    
	 * @date:  2018年7月25日 下午2:05:19   
	 * @param: @return      
	 * @return: WebEntity<UserEntity>      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/user/getUserByPhone", produces = { "application/json" })
	public WebEntity<UserEntity> getUserByPhone(String phone){
		LOGGER.info("UserController.getUserByPhone 传入参数:{}",phone);
		UserEntity userEntityByPhone = userEntityService.getUserEntityByPhone(phone);
		return new WebEntity<UserEntity>(EnumError.SUCCESS,userEntityByPhone);
	}
	
	/**
	 * 保存用户
	 * @param userEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/saveUser", produces = { "application/json" })
	public WebEntity<String> saveUser(UserEntity userEntity){
		LOGGER.info("UserController.saveUser 传入参数:{}",JSONObject.toJSONString(userEntity));
		userEntityService.saveUserEntity(userEntity);
		return new WebEntity<String>(EnumError.USER_SAVE_SUCCESS,"0");
	}
	
	
}
