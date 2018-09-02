package com.shop.api.exception;

import com.alibaba.fastjson.JSONObject;

public class BusinessException extends BaseException {
    /**
     * 
     */
    private static final long serialVersionUID = -2785553455811183838L;

    private EnumError error;

    private String key;

    private String value;


    public BusinessException(EnumError error) {
        super(error.getCode(), error.getMessage());
        this.error = error;
    }
    public BusinessException(int error,String message) {
        super(error, message);
    }
    public BusinessException(EnumError error,  Object value) {
        this(error);
        this.value = JSONObject.toJSONString(value);
    }
    public BusinessException(EnumError error, String key, Object value) {
        this(error, value);
        this.key = key;
    }

    public BusinessException(EnumError err, Throwable cause) {
        super(err.getCode(), err.getMessage(), cause);
        error = err;
    }

    public BusinessException(EnumError err, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(err.getCode(), err.getMessage(), cause, enableSuppression, writableStackTrace);
        error = err;
    }


    public EnumError getError() {
        return error;
    }

    public String getKey() {
        return key;
    }

    protected void setKey(String key){
    	this.key = key;
    }
    
    public Object getValue() {
        return value;
    }
    
    protected void setValue(String value) {
    	this.value = value;
    }

    @Override
    public String toString() {
        if (error != null) {
            String s = getClass().getName();
            if (this.getCause() != null) {
                return s + ": [" + error.name() + "][" + error.getCode() + "][" + this.getCause() + "]";
            }
            return s + ": [" + error.name() + "][" + error.getCode() + "][" + error.getMessage() + "]";
        } else {
            return super.toString();
        }
    }
}
