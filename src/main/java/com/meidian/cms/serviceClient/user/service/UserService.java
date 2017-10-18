package com.meidian.cms.serviceClient.user.service;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.user.User;

public interface UserService {

        ServiceResult<User> getUserByMobileAndPassword(String mobile,String password);
}
