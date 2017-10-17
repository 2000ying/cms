package com.meidian.cms.serviceClient.user.dao;

import com.meidian.cms.serviceClient.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {


}
