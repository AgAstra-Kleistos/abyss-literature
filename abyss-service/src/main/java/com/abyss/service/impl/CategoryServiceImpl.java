package com.abyss.service.impl;

import com.abyss.constant.MessageConstant;
import com.abyss.constant.StatusConstant;
import com.abyss.context.BaseContext;
import com.abyss.dto.CategoryDTO;
import com.abyss.dto.CategoryPageQueryDTO;
import com.abyss.entity.Category;
import com.abyss.exception.DeletionNotAllowdException;
import com.abyss.mapper.CategoryMapper;
import com.abyss.mapper.LiteratureMapper;
import com.abyss.mapper.NovelMapper;
import com.abyss.result.PageResult;
import com.abyss.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类模块业务层
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    //注入Mapper层
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private LiteratureMapper literatureMapper;
    @Autowired
    private NovelMapper novelMapper;

    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void save(CategoryDTO categoryDTO) {

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        category.setStatus(StatusConstant.DISABLE);

        categoryMapper.insert(category);

    }

    /**
     * 根据id删除分类
     * @param id
     */
    @Override
    public void deleteById(Long id) {

        Integer count = literatureMapper.countByCategoryId(id);

        if(count > 0){
            throw new DeletionNotAllowdException(MessageConstant.CATEGORY_BE_RELATED_BY_LITERATURE);
        }
        count = novelMapper.countByCategoryId(id);
        if(count > 0){
            throw new DeletionNotAllowdException(MessageConstant.CATEGORY_BE_RELATED_BY_NOVEL);
        }

        categoryMapper.deleteById(id);

    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据类型查询发分类
     * @param type
     * @return
     */
    @Override
    public List<Category> list(Integer type) {

        return categoryMapper.list(type);
    }

    /**
     * 启用、禁用分类
     * @param status
     */
    @Override
    public void startOrStop(Integer status, Long id) {

        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();

        categoryMapper.update(category);
    }

    /**
     * 修改分类
     * @param categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {

        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO, category);

        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);

    }
}
