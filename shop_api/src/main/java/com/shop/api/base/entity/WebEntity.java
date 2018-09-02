/**
 * @Description:
 * @version: V1.0
 */
package com.shop.api.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.shop.api.exception.BaseException;
import com.shop.api.exception.EnumError;


/**
 * WEB回应信息
 *
 * @param <T>
 * @ClassName: WebResponseEntity
 * @Description:
 */
public class WebEntity<T> {

    /**
     * 状态码
     */
	@JSONField(name="errcode")
    private int errcode;
    /**
     * 消息内容
     */
    @JSONField(name="errorMsg")
    private String errorMsg;
    /**
     * 正确时返回的数据
     */
    @JSONField(name="data")
    private T data;

    public WebEntity() {
    }

    public WebEntity(int code, String message) {
        this(code, message, null);
    }

    public WebEntity(EnumError error) {
        this(error.getCode(), error.getMessage());
    }

    public WebEntity(EnumError error, T data) {
        this(error.getCode(), error.getMessage(), data);
    }

    public WebEntity(int code, String message, T data) {
    	errcode = code;
        errorMsg = message;
        this.data = data;
    }

    public WebEntity(BaseException ex) {
        this(ex.getCode(), ex.getMessage());
    }
    
    /**
     * Gets the error no.
     *
     * @return the error no
     */
    public int getErrCode() {
        return errcode;
    }

    /**
     * Sets the error no.
     *
     * @param errorNo the new error no
     */
    public void setErrCode(int errcode) {
        this.errcode = errcode;
    }

    /**
     * Gets the error msg.
     *
     * @return the error msg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Sets the error msg.
     *
     * @param errorMsg the new error msg
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data.
     *
     * @param data the new data
     */
    public void setData(T data) {
        this.data = data;
    }

}
