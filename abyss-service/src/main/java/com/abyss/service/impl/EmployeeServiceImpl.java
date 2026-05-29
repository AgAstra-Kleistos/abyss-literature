package com.abyss.service.impl;

import com.abyss.constant.MessageConstant;
import com.abyss.constant.PasswordConstant;
import com.abyss.constant.StatusConstant;
import com.abyss.context.BaseContext;
import com.abyss.dto.EmployeeDTO;
import com.abyss.dto.EmployeeLoginDTO;
import com.abyss.dto.EmployeePageQueryDTO;
import com.abyss.entity.Employee;
import com.abyss.exception.AccountLockedException;
import com.abyss.exception.AccountNotFoundException;
import com.abyss.exception.PasswordErrorException;
import com.abyss.mapper.EmployeeMapper;
import com.abyss.result.PageResult;
import com.abyss.service.EmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    // 注入Mapper
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {

        //获取数据
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if(employee == null){
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码对比
        //TODO 后期后期需要进行md5加密，然后再进行比对
        if(!password.equals(employee.getPassword())){
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if(employee.getStatus() == StatusConstant.DISABLE){
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //返回实体类对象
        return employee;
    }

    /**
     * 新增员工
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        //设置账号默认启用状态
        employee.setStatus(StatusConstant.ENABLE);
        //设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        employeeMapper.insert(employee);
    }

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {

        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {

        Employee employee = employeeMapper.getById(id);

        employee.setPassword("****");

        return employee;
    }

    /**
     * 启用、禁用员工账号
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {

        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();

        employeeMapper.update(employee);
    }

    /**
     * 编辑员工账号信息
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);

    }

}
