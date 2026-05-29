package com.abyss.service;

import com.abyss.dto.EmployeeDTO;
import com.abyss.dto.EmployeeLoginDTO;
import com.abyss.dto.EmployeePageQueryDTO;
import com.abyss.entity.Employee;
import com.abyss.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 启用、禁用员工账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 编辑员工账号信息
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}
