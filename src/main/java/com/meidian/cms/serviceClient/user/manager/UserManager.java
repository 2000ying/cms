package com.meidian.cms.serviceClient.user.manager;

import com.meidian.cms.serviceClient.user.User;

import java.util.List;

public interface UserManager {

    User getUserByMobileAndPassword(String mobile,String password);

    List<User> getUserByIdIn(List<Long> userIds);
}
