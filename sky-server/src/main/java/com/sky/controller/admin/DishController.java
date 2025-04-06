package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zrh
 * @version 1.0.0
 * @title DishController
 * @description <>
 * @create 2025/3/27 22:32
 **/
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping()
    @ApiOperation("新增菜品")
    public Result<Integer> add(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}", dishDTO);
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        int result =dishService.addDish(dish,flavors);
        return Result.success(result);
    }

    @PutMapping()
    @ApiOperation("修改菜品")
    public Result<Integer> edit(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}", dishDTO);
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        int result =dishService.addDish(dish,flavors);
        return Result.success(result);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result<String> startOrStop(@PathVariable("status") Integer status, @RequestParam("id") Long id){
        log.info("菜品起售停售：{}", status);
        dishService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("回显菜品")
    public Result<DishVO> dishById(@PathVariable("id") Long id){
        log.info("回显菜品：{}", id);
        DishVO dish = dishService.getDishById(id);
        return Result.success(dish);
    }

    @DeleteMapping()
    @ApiOperation("回显菜品")
    public Result<DishVO> deleteDish(@RequestParam("ids") List<Long> idList){
        log.info("回显菜品：{}", idList);
        dishService.deleteDishById(idList);
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }
}
