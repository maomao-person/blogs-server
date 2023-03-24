package com.tcj.blogs.mapper;

import com.tcj.blogs.entity.User;
import com.tcj.blogs.entity.login.LoginArguments;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findUser(@Param("user") LoginArguments loginArguments);
    User insertUser(User user);
}
