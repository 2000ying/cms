package com.meidian.cms.serviceClient.user.dao;

import com.meidian.cms.serviceClient.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User,Long> {


    User getUserByMobileAndPassword(String mobile,String password);

    List<User> getUserByIdIn(List<Long> userIds);
}
