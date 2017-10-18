package com.meidian.cms.common.Enum;

public enum ErrorCode {

    LOGIN_ERROR(100,"手机号活密码错误"),
    SERVICE_ERROR(200,"服务发生异常");


    Integer code;

    private String message;


    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
