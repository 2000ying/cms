package com.meidian.cms.serviceClient.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.serviceClient.user.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void getUserByMobileAndPassword() throws Exception {

        ServiceResult<User> userServiceResult = userService.getUserByMobileAndPassword("1234567","1234567");

        logger.info(JSONObject.toJSONString(userServiceResult.getBody()));

        Assert.assertTrue(userServiceResult.getSuccess());
    }

}