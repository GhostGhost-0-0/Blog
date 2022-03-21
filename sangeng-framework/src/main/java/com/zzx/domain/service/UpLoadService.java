package com.zzx.domain.service;

import com.zzx.config.CloudStorageConfig;
import com.zzx.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-03-16 11:35
 **/
public abstract class UpLoadService {

    protected CloudStorageConfig config;

    public abstract ResponseResult upLoadOss(MultipartFile file);
}
