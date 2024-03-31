package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * @author zrh
     * @date 2024/3/28 22:46
     * @description <新增员工>
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * @author zrh
     * @date 2024/3/31 15:14
     * @description <分页查询>
     * @param employeePageQueryDTO
     * @return PageResult
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * @author zrh
     * @date 2024/3/31 17:57
     * @description <启用禁用员工账号>
     * @param status
     * @param id

     */
    void startOrStop(Integer status, Long id);

    /**
     * @author zrh
     * @date 2024/3/31 18:48
     * @description <根据员工id查询员工>
     * @param id
     * @return Employee
     */
    Employee getById(Long id);

    /**
     * @author zrh
     * @date 2024/3/31 19:18
     * @description <更新员工信息>
     * @param employeeDTO

     */
    void update(EmployeeDTO employeeDTO);
}
