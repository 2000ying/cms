package com.meidian.cms.serviceClient.user.service;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.user.User;

import java.util.List;

public interface UserService {

    ServiceResult<User> getUserByMobileAndPassword(String mobile,String password);

    ServiceResult<List<User>> getUserByIdIn(List<Long> userIds);
}
