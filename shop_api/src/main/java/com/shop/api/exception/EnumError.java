package com.shop.api.exception;

import com.shop.api.utils.enumeration.EnumUtils;

/**
 * 错误码 所有的错误码都定义在这里 错误码划分： 0 - 操作成功 其他 操作失败
 */
public enum EnumError {
	SUCCESS(0, "请求成功"), 
	UNKNOWN_ERROR(-1, "未知错误"), 
	PARAM_ERROR(1, "参数异常"),
	
	USER_SAVE_SUCCESS(0,"用户保存成功"),
	USER_WECHAT_REGISTER_SUCCESS(0,"注册成功"), 
	USER_EXIT_SUCCESS(0,"退出登陆"),
	USER_EXIT_FAIL(1,"退出登陆失败");

	private int errNo;
	private String errMsg;

	EnumError(int errNo, String errMsg) {
		this.errNo = errNo;
		this.errMsg = errMsg;
	}

	public String getMessage() {
		return errMsg;
	}

	public int getCode() {
		return errNo;
	}

	public static EnumError nameOf(String name) {
		return EnumUtils.valueOf(EnumError.class, name);
	}
}
