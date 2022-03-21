package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.entity.Link;


/**
 * 友链(SgLink)表服务接口
 *
 * @author makejava
 * @since 2022-02-24 14:28:26
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

