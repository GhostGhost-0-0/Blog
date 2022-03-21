package com.zzx.domain.service.impl;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.zzx.config.CloudStorageConfig;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.service.UpLoadService;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.utils.PathUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-03-02 19:06
 **/
@Service
public class UpLoadServiceImpl extends UpLoadService {

    //七牛文件上传管理器
    private UploadManager uploadManager;
    private String token;
    //七牛认证管理
    private Auth auth;

    public UpLoadServiceImpl(CloudStorageConfig config) {
        this.config = config;
        init();
    }

    private void init() {
        //构造一个带指定Zone对象的配置类，注意这里的Zone.zone0需要根据主机选择
        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
        auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        token = auth.uploadToken(config.getBucketName());
    }


    @Override
    public ResponseResult upLoadOss(MultipartFile file) {
        //获取文件的名称
        String fileName = file.getOriginalFilename();
        //使用工具类根据上传文件生成唯一图片名称
        String imgName = PathUtils.generateFilePath(fileName);
        if (file.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_NULL_ERROR);
        }
        try {
            FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
            Response res = uploadManager.put(fileInputStream, imgName, token,null,null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);

            String path = config.getDomain() + "/" + putRet.key;
            //这个return path是获得到的外链地址，通过这个地址可以直接打开图片
            return ResponseResult.okResult(path);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FILE_NULL_ERROR);
    }
}
