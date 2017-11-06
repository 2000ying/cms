package com.meidian.cms.common.exception;

public class BusinessException extends Exception {

    private Integer errorCode;

    public BusinessException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
