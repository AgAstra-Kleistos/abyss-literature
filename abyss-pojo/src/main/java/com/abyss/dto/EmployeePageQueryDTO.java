package com.abyss.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {
    //Serializable接口，实现序列化接口，可以进行序列化

    //员工姓名
    private String name;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;
}
