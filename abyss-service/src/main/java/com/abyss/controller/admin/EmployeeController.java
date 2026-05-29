package com.abyss.controller.admin;

import com.abyss.constant.JwtClaimsConstant;
import com.abyss.dto.EmployeeDTO;
import com.abyss.dto.EmployeeLoginDTO;
import com.abyss.dto.EmployeePageQueryDTO;
import com.abyss.entity.Employee;
import com.abyss.properties.JwtProperties;
import com.abyss.result.PageResult;
import com.abyss.result.Result;
import com.abyss.service.EmployeeService;
import com.abyss.utils.JwtUtils;
import com.abyss.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "员工相关接口")
@Slf4j
@RequestMapping("/admin/employee")
@RestController
public class EmployeeController {

    // 注入Service层
    @Autowired
    private EmployeeService employeeService ;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @Operation(description = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> Login(@RequestBody EmployeeLoginDTO employeeLoginDTO){

        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtils.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        //返回数据
        return Result.success(employeeLoginVO);

    }


    /**
     * 退出
     * @return
     */
    @Operation(description = "退出登录")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工
     * @return
     */
    @Operation(description = "新增员工")
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO){

        log.info("新增员工：{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();

    }

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Operation(description = "员工分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询：{}", employeePageQueryDTO);

        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Operation(description = "根据id查询员工")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){

        log.info("根据id查询员工：{}", id);

        Employee employee = employeeService.getById(id);

        return Result.success(employee);
    }

    /**
     * 启用、禁用员工账号
     * @param status
     * @param id
     * @return
     */
    @Operation(description = "启用、禁用员工账号")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id){

        log.info("启用、禁用员工账号：{}", status, id);

        employeeService.startOrStop(status, id);

        return Result.success();

    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @Operation(description = "编辑员工信息")
    @PutMapping
    public Result update(EmployeeDTO employeeDTO){

        log.info("编辑员工信息：{}", employeeDTO);

        employeeService.update(employeeDTO);

        return Result.success();
    }

}
