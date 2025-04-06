package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zrh
 * @version 1.0.0
 * @title UserMapper
 * @description <>
 * @create 2025/4/6 21:05
 **/
@Mapper
public interface UserMapper {
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    int insert(User user);
}
