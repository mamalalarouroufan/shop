package com.shop.web.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.api.base.controller.BaseController;
import com.shop.api.base.entity.WebEntity;
import com.shop.api.exception.BusinessException;
import com.shop.api.exception.EnumError;
import com.shop.api.utils.web.WebUtil;

/**
 * 
 * @ClassName  LoginController   
 * @Description (登陆操作)   
 * @author: 王杰
 * @date:   2018年8月1日 上午12:16:56   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
@Controller
public class LoginController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	/**
	 * @throws BusinessException 
	 * 
	 * @Title: exit   
	 * @Description: (退出登陆)   
	 * @return      
	 * @return WebEntity<String>  
	 * @author 王杰
	 * @date 2018年8月1日 下午3:06:47   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/login/exit", produces = { "application/json" })
	public WebEntity<String> exit() throws BusinessException {
		LOGGER.info("");
		WebUtil.removeWechatlogin(getRequest(), getResponse());
		return new WebEntity<String>(EnumError.USER_EXIT_SUCCESS, null);
	}
}
