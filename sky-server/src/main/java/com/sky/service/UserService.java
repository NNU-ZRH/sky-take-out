package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @author zrh
 * @version 1.0.0
 * @title UserService
 * @description <>
 * @create 2025/4/6 19:44
 **/
public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}
