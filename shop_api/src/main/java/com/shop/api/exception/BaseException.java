package com.shop.api.exception;

/**
 */
public class BaseException extends Exception {
	private static final long serialVersionUID = -4006848506010301440L;
	private int code = -1;

    public BaseException() {
    }

    public BaseException(EnumError err) {
        this(err.getCode(), err.getMessage());
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    public BaseException(EnumError err, Throwable cause) {
        this(err.getCode(), err.getMessage(), cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(int code, String message, Throwable cause,
                         boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        return s + ": [" + getCode() + "][" + getMessage() + "]";
    }
}
