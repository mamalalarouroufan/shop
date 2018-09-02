package com.shop.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shop.api.entity.user.LoginVo;
import com.shop.api.utils.web.WebUtil;

/**
 * 
 * @ClassName  LoginInterceptor   
 * @Description (拦截用户登陆信息)   
 * @author: 王杰
 * @date:   2018年7月27日 下午3:06:42   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class LoginInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOGGER.info("执行登陆拦截器方法");
		LoginVo loginVo = WebUtil.getWechatLogin(request);
		if(null==loginVo){
			//没有登陆是需要跳转登陆
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}
}
