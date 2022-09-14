package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.CategoryListDto;
import com.zzx.domain.entity.Category;



/**
 * 分类表(Category)表服务接口
 *
 * @author 那个小楠瓜
 * @since 2022-02-23 18:32:59
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult selectCategoryListPage(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    ResponseResult getCategoryDetail(Long categoryId);

    ResponseResult addCategory(CategoryListDto categoryListDto);

    ResponseResult updateCategory(CategoryListDto categoryListDto);

    ResponseResult deleteCategory(Long categoryId);

    ResponseResult getCategoryNormal();

}

