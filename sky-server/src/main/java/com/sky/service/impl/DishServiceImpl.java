package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zrh
 * @version 1.0.0
 * @title DishServiceImpl
 * @description <description class purpose>
 * @create 2025/3/27 22:45
 **/
@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        //用pageHelper实现分页查询
        //开始分页查询
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        //条件查询
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public int addDish(Dish dish, List<DishFlavor> dishFlavorList) {
        //先删后插
        Long dishId1 = dish.getId();
        if (dish.getId() != null) {
            dishMapper.deleteDishById(dishId1);
            dishMapper.deleteDishFlavorById(dishId1);
        }
        //添加到菜品表
        int result = dishMapper.addDish(dish);

        //通过xml的设置获取主键
        Long dishId = dish.getId();

        //添加到菜品口味表
        if (dishFlavorList != null && dishFlavorList.size() > 0 && !"".equals(dishFlavorList.get(0).getName()) && !"[]".equals(dishFlavorList.get(0).getValue())) {
            for (DishFlavor dishFlavor : dishFlavorList) {
                dishFlavor.setDishId(dishId);
            }
            dishMapper.addDishFlavor(dishFlavorList);
        }
        return result;
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        dishMapper.setStatus(status, id);
    }

    @Override
    public DishVO getDishById(Long id) {
        DishVO dishVO = new DishVO();
        Dish dish = dishMapper.getDishById(id);
        List<DishFlavor> flavors = dishMapper.getDishFlavorById(id);
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    @Transactional
    public void deleteDishById(List<Long> idList) {
        //启用的菜品不能删除
        for (Long id : idList) {
            Dish dish = dishMapper.getDishById(id);
            if (dish.getStatus() == 1) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //有关联套餐的不能删
        List<Map<String, Object>> setMeal = dishMapper.getSetMeal(idList);
        if (setMeal != null && setMeal.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品、删除口味
        for (Long id : idList) {
            dishMapper.deleteDishById(id);
            dishMapper.deleteDishFlavorById(id);
        }
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishMapper.getDishFlavorById(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }
}
