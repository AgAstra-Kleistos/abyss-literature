package com.abyss.service;

import com.abyss.dto.CategoryDTO;
import com.abyss.dto.CategoryPageQueryDTO;
import com.abyss.entity.Category;
import com.abyss.result.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * 新增分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 根据id删除分类
     * @param id
     */
    void deleteById(Long id);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    List<Category> list(Integer type);

    /**
     * 启用、禁用分类
     * @param status
     */
    void startOrStop(Integer status, Long id);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);
}
