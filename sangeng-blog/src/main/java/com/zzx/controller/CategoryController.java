package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: SGBlog
 * @Description 前台分类接口
 * @Author: 那个小楠瓜
 * @create: 2022-02-23 18:40
 **/
@RestController
@RequestMapping("/category")
@Api(description = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "查询分类列表")
    @ApiOperation(value = "分类列表", notes = "获取所有的分类标签")
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();
    }
}
