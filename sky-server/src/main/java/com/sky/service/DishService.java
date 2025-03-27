package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

/**
 * @author zrh
 * @version 1.0.0
 * @title DishService
 * @description <description class purpose>
 * @create 2025/3/27 22:45
 **/
public interface DishService {
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
