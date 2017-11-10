package com.meidian.cms;

import com.meidian.cms.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsApplicationTests {

	@Autowired
	private RedisUtil redisUtil;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testRedis(){
		redisUtil.setex("test",100,"test");
		System.out.println(redisUtil.getString("test"));
	}

	@Test
	public void  testNum(){
		Integer tem = 10;
		System.out.println(714/tem++);
		System.out.println(tem);
	}

}
