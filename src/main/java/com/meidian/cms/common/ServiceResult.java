package com.meidian.cms.common;

import java.io.Serializable;

/**
 * Title: com.meidian.auth.common<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 * @author 张中凯
 *         2017/8/28
 */
public class ServiceResult<T> implements Serializable {
    private Boolean success = Boolean.valueOf(true);
    private int errorCode;
    private String message;
    private T body;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
