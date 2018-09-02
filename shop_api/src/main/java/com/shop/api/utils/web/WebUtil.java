package com.shop.api.utils.web;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.shop.api.entity.user.LoginVo;
import com.shop.api.exception.BusinessException;
import com.shop.api.exception.EnumError;
import com.shop.api.utils.MD5.MD5Util;
import com.shop.api.utils.UUID.UUIDUtil;
import com.shop.api.utils.constant.ConstantKey;
import com.shop.api.utils.redis.RedisUtil;

@Component
public class WebUtil {

	private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

	/** 获取redis操作类 **/
	private static RedisUtil redisUtil;

	private static final String idKey = "W_J_XIAXIANGDIAN_DETECTIVE_LAWLIET"; // 自定义密钥

	@Resource
	public void setRedisUtil(RedisUtil redisUtil) {
		WebUtil.redisUtil = redisUtil;
	}

	/**
	 * 
	 * @Title: getWechatLogin   
	 * @Description: (获取微信登录信息)   
	 * @return      
	 * @return LoginVo  
	 * @author 王杰
	 * @date 2018年7月27日 下午3:44:53   
	 * @throws
	 */
	public static LoginVo getWechatLogin(HttpServletRequest request) {
		Cookie cookieToekn = getCookie(request, ConstantKey.COOKIE_TOKEN_WX_KEY);
		Cookie cookieTimestamp = getCookie(request, ConstantKey.COOKIE_TOKEN_TIMESTAMP_KEY);
		Cookie cookieRandom = getCookie(request, ConstantKey.COOKIE_TOKEN_RANDOM_KEY);
		Cookie wechatCode = getCookie(request, ConstantKey.COOKIE_WECHATCODE_KEY);
		if (cookieToekn == null || cookieTimestamp == null || cookieRandom == null || wechatCode==null) {
			return null;
		}

		String reidsLoginKey = MD5Util
				.getMD5Encode(cookieToekn.getValue() + cookieTimestamp.getValue() + cookieRandom.getValue() + idKey);

		return redisUtil.get(String.format(ConstantKey.WECHAT_LOGIN_KEY, wechatCode.getValue(), reidsLoginKey),
				LoginVo.class);
	}

	/**
	 * 
	 * @Title: setWechatLogin   
	 * @Description: (设置微信登录信息)   
	 * @param response
	 * @param loginVo      
	 * @return void  
	 * @author 王杰
	 * @date 2018年7月27日 下午3:57:02   
	 * @throws
	 */
	public static void setWechatLogin(HttpServletResponse response, LoginVo loginVo) {

		String cookieToeknValue = MD5Util
				.getMD5Encode(loginVo.getId() + loginVo.getOpenId() + System.currentTimeMillis());
		String cookieTimestampValue = MD5Util.getMD5Encode(System.currentTimeMillis() + "");
		String cookieRandomValue = MD5Util.getMD5Encode(UUIDUtil.getRandomString(6));
		String reidsLoginKey = MD5Util
				.getMD5Encode(cookieToeknValue + cookieTimestampValue + cookieRandomValue + idKey);

		addCookie(response, ConstantKey.COOKIE_TOKEN_WX_KEY, "/", cookieToeknValue, null, ConstantKey.COOKIE_MAX_AGE);
		addCookie(response, ConstantKey.COOKIE_TOKEN_TIMESTAMP_KEY, "/", cookieTimestampValue, null,
				ConstantKey.COOKIE_MAX_AGE);
		addCookie(response, ConstantKey.COOKIE_TOKEN_RANDOM_KEY, "/", cookieRandomValue, null,
				ConstantKey.COOKIE_MAX_AGE);
		addCookie(response, ConstantKey.COOKIE_WECHATCODE_KEY, "/", loginVo.getWechatCode(), null,
				ConstantKey.COOKIE_MAX_AGE);

		redisUtil.set(String.format(ConstantKey.WECHAT_LOGIN_KEY, loginVo.getWechatCode(), reidsLoginKey), loginVo,
				ConstantKey.COOKIE_MAX_AGE);

	}
	
	/**
	 * @throws BusinessException 
	 * 
	 * @Title: removeWechatlogin   
	 * @Description: (移除登陆信息)   
	 * @param request
	 * @param response      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月1日 下午3:10:08   
	 * @throws
	 */
	public static void removeWechatlogin(HttpServletRequest request,HttpServletResponse response) throws BusinessException{
		Cookie cookieToekn = getCookie(request, ConstantKey.COOKIE_TOKEN_WX_KEY);
		Cookie cookieTimestamp = getCookie(request, ConstantKey.COOKIE_TOKEN_TIMESTAMP_KEY);
		Cookie cookieRandom = getCookie(request, ConstantKey.COOKIE_TOKEN_RANDOM_KEY);
		Cookie wechatCode = getCookie(request, ConstantKey.COOKIE_WECHATCODE_KEY);
		
		if (cookieToekn == null || cookieTimestamp == null || cookieRandom == null || wechatCode==null) {
			throw new BusinessException(EnumError.USER_EXIT_FAIL);
		}
		
		String reidsLoginKey = MD5Util
				.getMD5Encode(cookieToekn.getValue() + cookieTimestamp.getValue() + cookieRandom.getValue() + idKey);

		removeCookie(response, cookieToekn);
		removeCookie(response, cookieTimestamp);
		removeCookie(response, cookieRandom);
		removeCookie(response, wechatCode);
		
		redisUtil.delete(String.format(ConstantKey.WECHAT_LOGIN_KEY, wechatCode.getValue(), reidsLoginKey));
	}

	/**
	 * 
	 * @param response
	 * @param name
	 * @param path
	 * @param value
	 * @param domain
	 *            域,cookie共享使用
	 * @param maxAge
	 *            如为空,则不设置时间.
	 */
	public static void addCookie(HttpServletResponse response, String name, String path, String value, String domain,
			Integer maxAge) {
		Cookie cookie = new Cookie(name, value);

		if (domain != null && !"".equalsIgnoreCase(domain)) {
			cookie.setDomain(domain);
		}

		if (path != null && !"".equalsIgnoreCase(path)) {
			cookie.setPath(path);
		} else {
			cookie.setPath("/");
		}

		if (maxAge != null) {
			if (maxAge > 0) {
				cookie.setMaxAge(maxAge);
			} else {
				cookie.setMaxAge(ConstantKey.COOKIE_MAX_AGE);
			}
		}
		response.addCookie(cookie);
	}

	public static void addCookie(HttpServletResponse response, String name, String path, String value, String domain) {
		Cookie cookie = new Cookie(name, value);

		if (domain != null && !"".equalsIgnoreCase(domain)) {
			cookie.setDomain(domain);
		}

		if (path != null && !"".equalsIgnoreCase(path)) {
			cookie.setPath(path);
		} else {
			cookie.setPath("/");
		}

		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, Cookie cookie) {
		if (cookie != null) {
			cookie.setPath("/");
			cookie.setValue("");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	public static void removeCookie(HttpServletResponse response, Cookie cookie, String domain) {
		if (cookie != null) {
			cookie.setPath("/");
			cookie.setValue("");
			cookie.setMaxAge(0);
			cookie.setDomain(domain);
			response.addCookie(cookie);
		}
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		if (cookie != null) {
			return cookie.getValue();
		} else {
			return null;
		}
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null || name == null || name.length() == 0)
			return null;
		Cookie cookie = null;

		log.debug("客户端传过来cookie 值  start.. ");
		for (int i = 0; i < cookies.length; i++) {
			log.debug("name= " + cookies[i].getName() + " , value= " + cookies[i].getValue());
		}
		log.debug("客户端传过来cookie 值  end.. ");
		
		for (int i = 0; i < cookies.length; i++) {
			if (!cookies[i].getName().equals(name))
				continue;
			cookie = cookies[i];
			if (request.getServerName().equals(cookie.getDomain()))
				break;
		}

		return cookie;
	}
}
