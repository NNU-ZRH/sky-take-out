package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * @author zrh
     * @date 2025/3/27 23:04
     * @description <分页查询>
     * @param dishPageQueryDTO
     * @return Page<DishVO>
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);


}
