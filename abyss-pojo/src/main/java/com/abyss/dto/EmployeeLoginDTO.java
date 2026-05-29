package com.abyss.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

// ApiModel用在类上，表明类的用途说明
@Schema(description = "员工登录时传递的数据模型")
@Data
public class EmployeeLoginDTO implements Serializable {

    //ApiModelProperty: 用在属性上，描述属性信息
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "密码")
    private String password;
}
