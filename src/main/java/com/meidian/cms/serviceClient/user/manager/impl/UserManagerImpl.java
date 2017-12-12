package com.meidian.cms.serviceClient.user.manager.impl;

import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.serviceClient.user.dao.UserDao;
import com.meidian.cms.serviceClient.user.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserManagerImpl implements UserManager{

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByMobileAndPassword(String mobile,String password) {
        return userDao.getUserByMobileAndPassword(mobile,password);
    }

    @Override
    public List<User> getUserByIdIn(List<Long> userIds) {
        return userDao.getUserByIdIn(userIds);
    }
}
