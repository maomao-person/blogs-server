package com.tcj.blogs.mapper;

import com.tcj.blogs.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findUser(@Param("user") User user);
    User insertUser(User user);
}
