package com.shop.web.develop.authority.service.xss;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * request信息封装类，用于判断、处理request请求中特殊字符
 * @Copyright 北京瑞友科技股份有限公司
 * @author xiaolei
 * @date 2014年9月28日
 * =================Modify Record=================
 * @Modifier			@date			@Content
 *
 */
public class XSSHttpRequestWrapper extends HttpServletRequestWrapper {
	private static final Logger log = LoggerFactory.getLogger(XSSHttpRequestWrapper.class);
	private Map<String, String[]> params = new HashMap<String, String[]>();

	/**
	 * 封装http请求
	 * @param request
	 */
	public XSSHttpRequestWrapper(HttpServletRequest request) {
		super(request);
		// 将参数表，赋予给当前的Map以便于持有request中的参数
		this.params.putAll(request.getParameterMap());
	}

	@Override
	public String getHeader(String name) {

		String value = super.getHeader(name);
		// 若开启特殊字符替换，对特殊字符进行替换
		if (XSSSecurityConfig.REPLACE.equals(XSSSecurityConstants.REPLACE_1)) {
			value = XSSSecurityManager.securityReplace(value);
		} else if (XSSSecurityConfig.REPLACE.equals(XSSSecurityConstants.REPLACE_2)) { // 实现转码

		}
		return value;
	}

	@Override
	public String getParameter(String name) {

		String value = super.getParameter(name);
		// 若开启特殊字符替换，对特殊字符进行替换
		if (XSSSecurityConfig.REPLACE.equals(XSSSecurityConstants.REPLACE_1)) {
			value = XSSSecurityManager.securityReplace(value);
		} else if (XSSSecurityConfig.REPLACE.equals(XSSSecurityConstants.REPLACE_2)) { // 实现转码

		}
		return value;
	}

	@Override
	public Map<String, String[]> getParameterMap() {

		Set<String> submitNames = params.keySet();
		for (String submitName : submitNames) {
			String[] submitValues = params.get(submitName);
			if (submitValues != null) {
				for (int i = 0; i < submitValues.length; i++) {
					if (StringUtils.isNotEmpty(submitValues[i])) {
						if (XSSSecurityConfig.REPLACE.equals(XSSSecurityConstants.REPLACE_1)) {
							submitValues[i] = XSSSecurityManager.securityReplace(submitValues[i]);
						} else if (XSSSecurityConfig.REPLACE.equals(XSSSecurityConstants.REPLACE_2)) { // 实现转码

						}
					}

				}
				params.put(submitName, submitValues);
			}
		}

		return params;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] submitValues = super.getParameterValues(name);
		if (submitValues != null) {
			for (int i = 0; i < submitValues.length; i++) {
				if (StringUtils.isNotEmpty(submitValues[i])) {
					if (XSSSecurityConfig.REPLACE.equals(XSSSecurityConstants.REPLACE_1)) {
						submitValues[i] = XSSSecurityManager.securityReplace(submitValues[i]);
					} else if (XSSSecurityConfig.REPLACE.equals(XSSSecurityConstants.REPLACE_2)) { // 实现转码

					}
				}
			}
		}

		return submitValues;
	}

	/**
	 * 校验头部，没有违规的数据，就返回false;
	 * 
	 * @return
	 */
	private boolean checkHeader() {

		Enumeration<String> headerParams = this.getHeaderNames();
		while (headerParams.hasMoreElements()) {
			String headerName = headerParams.nextElement();
			String headerValue = super.getHeader(headerName);
			if (XSSSecurityManager.matches(headerValue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 校验参数，没有违规的数据，就返回false;
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private boolean checkParameter() {

		// 特殊url校验，不使用通用url正则校验
		if (XSSSecurityConfig.IS_CHECK_URL) {
			List<Map<String, Object>> checkUrlList = XSSSecurityManager.checkUrlMatcherList;
			for (Map matchMap : checkUrlList) {
				String requestURL = super.getRequestURL().toString();
				String matcherURL = matchMap.keySet().iterator().next().toString();
				// 请求url匹配配置的特殊url
				if (requestURL.contains(matcherURL)) {
					if (this.matches(matchMap.get(matcherURL).toString())) {
						log.info("#疑似跨站脚本攻击！请求url：{}", requestURL);
						return true;
					} else {
						return false;
					}
				}
			}
		}

		// 通用url进行校验
		Map<String, String[]> submitParams = super.getParameterMap();
		Set<String> submitNames = submitParams.keySet();
		for (String submitName : submitNames) {
			String[] submitValues = submitParams.get(submitName);

			for (String submitValue : (String[]) submitValues) {
				if (XSSSecurityManager.matches(submitValue)) {
					log.info("#疑似跨站脚本攻击！参数名称：{}；录入信息:{}", submitName, submitValue);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 特殊url匹配请求参数中是否含特殊字符
	 * @param regex
	 * @return
	 */
	private boolean matches(String regex) {

		Pattern checkUrlPattern = Pattern.compile(regex);

		Map<String, String[]> submitParams = super.getParameterMap();
		Set<String> submitNames = submitParams.keySet();
		for (String submitName : submitNames) {
			String[] submitValues = submitParams.get(submitName);
			for (String submitValue : (String[]) submitValues) {
				if (!(submitValue == null) && !"".equals(submitValue.trim())
						&& checkUrlPattern.matcher(submitValue).matches()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 没有违规的数据，就返回false;
	 * 若存在违规数据，根据配置信息判断是否跳转到错误页面
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public boolean validateParameter(HttpServletResponse response) throws ServletException, IOException {

		// 开始header校验，对header信息进行校验
		if (XSSSecurityConfig.IS_CHECK_HEADER) {
			if (this.checkHeader()) {
				return true;
			}
		}
		// 开始parameter校验，对parameter信息进行校验
		if (XSSSecurityConfig.IS_CHECK_PARAMETER) {
			if (this.checkParameter()) {
				return true;
			}
		}

		// sql注入检测
		if (checkSqlInjection()) {
			return true;
		}

		return false;
	}

	/**
	 * @describe: SQL注入校验
	 * @author zhangzubing
	 * @date 2017年9月7日
	 * @return
	 * @throws 
	 * @Version 1.0
	 */
	@SuppressWarnings("rawtypes")
	private boolean checkSqlInjection() {
		Map parametersMap = super.getParameterMap();
		Iterator it = parametersMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String[] value = (String[]) entry.getValue();
			for (int i = 0; i < value.length; i++) {
				if (null != value[i] && XSSSecurityManager.sqlInjectionMatches(value[i])) {
					log.info("#疑似SQL注入攻击！参数名称：{}；录入信息:{}", entry.getKey(), value[i]);
					return true;
				}
			}
		}

		return false;
	}
}
