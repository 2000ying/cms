package com.meidian.cms.serviceClient.user.dao;

import com.meidian.cms.serviceClient.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testUserDao(){
        Boolean test = userDao.exists((long)0);
        Assert.assertTrue(!test);
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setMobile("123");
        user.setName("test");
        user.setPassword("123");
        userDao.save(user);
    }

}