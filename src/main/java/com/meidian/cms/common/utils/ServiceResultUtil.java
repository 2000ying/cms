package com.meidian.cms.common.utils;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Result;
import com.meidian.cms.common.ServiceResult;

import java.util.List;

public class ServiceResultUtil {
    public static ServiceResult returnFalse(Integer errorCode, String msg){
        ServiceResult result = new ServiceResult();
        result.setSuccess(false);
        result.setMessage(msg);
        return result;
    }

    public static ServiceResult returnFalse(){
        ServiceResult result = new ServiceResult();
        result.setSuccess(false);
        result.setErrorCode(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        result.setMessage(ErrorCode.BUSINESS_DEFAULT_ERROR.getMessage());
        return result;
    }

    public static ServiceResult returnFalse(Integer errorCode){
        ServiceResult result = new ServiceResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        return result;
    }

    public static ServiceResult returnTrue(){
        ServiceResult result = new ServiceResult();
        result.setSuccess(true);
        result.setErrorCode(ErrorCode.SUCCESS.getCode());
        result.setMessage(ErrorCode.SUCCESS.getMessage());
        return result;
    }

    public static ServiceResult returnTrue(String msg){
        ServiceResult result = new ServiceResult();
        result.setSuccess(true);
        result.setErrorCode(ErrorCode.SUCCESS.getCode());
        result.setMessage(msg);
        return result;
    }

    public static ServiceResult returnTrue(Object body){
        ServiceResult result = new ServiceResult();
        result.setSuccess(true);
        result.setErrorCode(ErrorCode.SUCCESS.getCode());
        result.setMessage(ErrorCode.SUCCESS.getMessage());
        result.setBody(body);
        return result;
    }
}
