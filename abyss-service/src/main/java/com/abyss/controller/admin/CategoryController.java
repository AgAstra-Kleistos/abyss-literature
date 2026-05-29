package com.abyss.controller.admin;

import com.abyss.dto.CategoryDTO;
import com.abyss.dto.CategoryPageQueryDTO;
import com.abyss.entity.Category;
import com.abyss.result.PageResult;
import com.abyss.result.Result;
import com.abyss.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@Tag(name = "分类模块相关接口")
@Slf4j
@RequestMapping("/admin/category")
@RestController
public class CategoryController {

    //注入Service层
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @Operation(description = "新增分类")
    @PostMapping
    public Result save(CategoryDTO categoryDTO){

        log.info("新增分类：{}", categoryDTO);

        categoryService.save(categoryDTO);

        return Result.success();

    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @Operation(description = "根据id删除分类")
    @DeleteMapping
    public  Result deleteById(Long id){

        log.info("根据id删除分类：{}", id);

        categoryService.deleteById(id);

        return Result.success();
    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Operation(description = "分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){

        log.info("分类分页查询： {}", categoryPageQueryDTO);

        PageResult pageResult= categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(pageResult);


    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @Operation(description = "根据类型查询分类")
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){

        log.info("根据类型查询分类：{}", type);

        List<Category> list = categoryService.list(type);

        return Result.success(list);
    }

    /**
     * 启用、禁用分类
     * @param status
     * @return
     */
    @Operation(description = "启用、禁用分类")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id){

        log.info("启用、禁用分类：{}, {}", status, id);

        categoryService.startOrStop(status, id);

        return Result.success();

    }

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @Operation(description = "修改分类")
    @PutMapping
    public Result update(CategoryDTO categoryDTO){

        log.info("修改分类：{}", categoryDTO);

        categoryService.update(categoryDTO);

        return Result.success();

    }
}
