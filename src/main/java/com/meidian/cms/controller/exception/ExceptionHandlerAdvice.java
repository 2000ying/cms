package com.meidian.cms.controller.exception;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Integers;
import com.meidian.cms.common.Result;
import com.meidian.cms.common.utils.ResultUtils;
import com.meidian.cms.common.Strings;
import com.meidian.cms.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public Result handleException(BusinessException e) {
        logger.error(e.getMessage(),e);
        return ResultUtils.returnFalse(Integers.defaultIfNll(e.getErrorCode(),ErrorCode.BUSINESS_DEFAULT_ERROR.getCode()),
                Strings.defaultIfNull(e.getMessage(),ErrorCode.BUSINESS_DEFAULT_ERROR.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        logger.error(e.getMessage(),e);
        return ResultUtils.returnFalse(ErrorCode.SERVICE_ERROR.getCode(),ErrorCode.SERVICE_ERROR.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleException(IllegalArgumentException e) {
        logger.error(e.getMessage(),e);
        return ResultUtils.returnFalse(ErrorCode.PARAM_ERROR.getCode(), Strings.defaultIfNull(e.getMessage(),ErrorCode.PARAM_ERROR.getMessage()));
    }
}
