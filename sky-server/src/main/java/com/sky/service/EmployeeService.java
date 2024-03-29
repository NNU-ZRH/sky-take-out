package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

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
}
