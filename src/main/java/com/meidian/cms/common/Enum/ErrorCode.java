package com.meidian.cms.common.Enum;

public enum ErrorCode {

    SUCCESS(1,"成功！"),
    PARAM_ERROR(100,"参数为空！"),
    BUSINESS_DEFAULT_ERROR(200,"业务异常"),
    SERVICE_ERROR(500,"服务发生异常,请联系啊相关人员！");


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
