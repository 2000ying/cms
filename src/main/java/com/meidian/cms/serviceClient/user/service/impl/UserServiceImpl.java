package com.meidian.cms.serviceClient.user.service.impl;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.controller.agreement.AgreementController;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.serviceClient.user.manager.UserManager;
import com.meidian.cms.serviceClient.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserManager userManager;


    @Override
    public ServiceResult<User> getUserByMobileAndPassword(String mobile,String password) {
        ServiceResult<User> result = new ServiceResult<User>();
        try{
            User user = userManager.getUserByMobileAndPassword(mobile,password);
            if (null != user){
                result.setSuccess(true);
                result.setBody(user);
            }else{
                result.setErrorCode(ErrorCode.LOGIN_ERROR.getCode());
                result.setSuccess(false);
                result.setMessage(ErrorCode.LOGIN_ERROR.getMessage());
            }
        }catch (Exception ex){
            logger.error("getUserByMobileAndPassword has error,The error is " + ex.getMessage(),ex);
            result.setSuccess(false);
            result.setErrorCode(ErrorCode.SERVICE_ERROR.getCode());
            result.setMessage(ErrorCode.SERVICE_ERROR.getMessage());
        }
        return result;
    }
}
