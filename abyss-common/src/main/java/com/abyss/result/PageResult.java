package com.abyss.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

//封装分页查询结果
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult implements Serializable {

    private long total; //记录总页数

    private List records; //当前页数据集合
}

