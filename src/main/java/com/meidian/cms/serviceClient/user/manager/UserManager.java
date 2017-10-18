package com.meidian.cms.serviceClient.user.manager;

import com.meidian.cms.serviceClient.user.User;

public interface UserManager {

    User getUserByMobileAndPassword(String mobile,String password);
}
