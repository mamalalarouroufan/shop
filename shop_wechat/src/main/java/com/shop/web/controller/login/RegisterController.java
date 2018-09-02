package com.shop.web.controller.login;

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
import com.shop.api.entity.wechat.bean.message.template.Data;
import com.shop.api.entity.wechat.bean.message.template.First;
import com.shop.api.entity.wechat.bean.message.template.Keynote;
import com.shop.api.entity.wechat.bean.message.template.Remark;
import com.shop.api.entity.wechat.bean.message.template.Template;
import com.shop.api.entity.wechat.message.service.TemplateMessageService;
import com.shop.api.exception.EnumError;

/**
 * 
 * @ClassName  RegisterController   
 * @Description (注册操作)   
 * @author: 王杰
 * @date:   2018年8月1日 上午12:19:11   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
@Controller
public class RegisterController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

	@Resource
	private UserEntityService userEntityService;
	@Resource
	private TemplateMessageService templateMessageService;

	/**
	 * @throws Exception 
	 * 
	 * @Title: registerUser   
	 * @Description: (微信公众号注册注册操作)   
	 * @param userEntity
	 * @param yzm
	 * @return      
	 * @return WebEntity  
	 * @author 王杰
	 * @date 2018年8月1日 上午12:23:29   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/login/wechatRegister", produces = { "application/json" })
	public WebEntity<String> registerUser(UserEntity userEntity, String yzm) throws Exception {
		LOGGER.info("/login/register 传入参数:{} 验证码:{}", JSONObject.toJSONString(userEntity), yzm);

		Data data = new Data();
		data.setFirst(new First("恭喜您注册成功","#173177"));
		data.setKeyword1(new Keynote("王杰", "#173177"));
		data.setKeyword2(new Keynote("18959271890", "#173177"));
		data.setKeyword3(new Keynote("2018-08-10 11:11:11", "#173177"));
		data.setRemark(new Remark("霞湘店欢迎您的到来,祝您天天都有好心情...", "#173177"));
		Template template = new Template();
		template.setTouser("o_nOa1TFmqaoh_N3ZLxtlFvsnDMo");
		template.setTemplate_id("SMizINIqpoAhjeTuTw9bLsSzFkjVpYVEKOEVAx_Sj1Q");
		template.setUrl("https://www.baidu.com");
		template.setData(data);
		templateMessageService.sendTemplateMessage("ceshi", template);
		return new WebEntity<String>(EnumError.USER_WECHAT_REGISTER_SUCCESS, null);
	}

}
