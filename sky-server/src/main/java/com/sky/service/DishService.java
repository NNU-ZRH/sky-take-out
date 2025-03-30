package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author zrh
 * @version 1.0.0
 * @title DishService
 * @description <description class purpose>
 * @create 2025/3/27 22:45
 **/
public interface DishService {
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    int addDish(Dish dish, List<DishFlavor> dishFlavorList);

    void startOrStop(Integer status, Long id);

    DishVO getDishById(Long id);

    void deleteDishById(List<Long> idList);
}
