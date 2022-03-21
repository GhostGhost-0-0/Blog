package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-02-23 18:32:59
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
