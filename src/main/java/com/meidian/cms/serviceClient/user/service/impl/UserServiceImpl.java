package com.meidian.cms.serviceClient.user.service.impl;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.controller.agreement.AgreementController;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.serviceClient.user.manager.UserManager;
import com.meidian.cms.serviceClient.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserManager userManager;


    @Override
    public ServiceResult<User> getUserByMobileAndPassword(String mobile,String password) {
        ServiceResult<User> result = new ServiceResult<User>();
        User user = userManager.getUserByMobileAndPassword(mobile,password);
        if (null == user){
            return ServiceResultUtil.returnFalse();
        }
        return ServiceResultUtil.returnTrue(user);
    }

    @Override
    public ServiceResult<List<User>> getUserByIdIn(List<Long> userIds) {
        List<User> companyList = userManager.getUserByIdIn(userIds);
        return ServiceResultUtil.returnTrue(companyList);
    }
}
