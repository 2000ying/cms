package com.meidian.cms.common.utils;

import com.meidian.cms.serviceClient.user.service.impl.UserServiceImplTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MD5UtilTest {
    private static final Logger logger = LoggerFactory.getLogger(MD5UtilTest.class);

    @Test
    public void md5() throws Exception {

        logger.info(MD5Util.md5("test"));
    }

}