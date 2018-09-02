package com.shop.api.entity.wechat.base;

/**
 * 微信请求状态数据
 */
public class BaseResult {

	/** 返回码 */
	private String errcode;

	/** 说明 */
	private String errmsg;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	@Override
	public String toString() {
		return "BaseResult [errcode=" + errcode + ", errmsg=" + errmsg + "]";
	}

}
