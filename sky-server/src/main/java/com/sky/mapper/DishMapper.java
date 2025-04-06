package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * @author zrh
     * @date 2025/3/27 23:04
     * @description <分页查询>
     * @return Page<DishVO>
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);


    @AutoFill(OperationType.INSERT)
    int addDish(Dish dish);

    int addDishFlavor(List<DishFlavor> flavors);

    @Update("update dish set status = #{status} where id = #{id}")
    void setStatus(Integer status, Long id);

    @Select("select * from dish where id = #{id}")
    Dish getDishById(Long id);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getDishFlavorById(Long id);

    @Delete("delete from dish where id = #{dishId1}")
    void deleteDishById(Long dishId1);

    @Delete("delete from dish_flavor where dish_id = #{dishId1}")
    void deleteDishFlavorById(Long dishId1);

    List<Map<String,Object>> getSetMeal(List<Long> idList);

    List<Dish> list(Dish dish);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);

}
