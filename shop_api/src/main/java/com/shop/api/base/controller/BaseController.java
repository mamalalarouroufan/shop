package com.shop.api.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	// 获取Request
	protected HttpServletRequest getRequest() {
		return RequestResponseContext.getRequest();
	}

	// 获取Response
	protected HttpServletResponse getResponse() {
		return RequestResponseContext.getResponse();
	}

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		RequestResponseContext.setRequest(request);
		RequestResponseContext.setResponse(response);
	}

}
