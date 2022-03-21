package com.zzx.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-03-02 18:36
 **/
@Data
@Configuration
public class CloudStorageConfig {

    /**
     * 七牛域名 domain
     */
    @Value("${oss.qiniu.domain}")
    private String domain;

    /**
     * 七牛 ACCESS_KEY
     */
    @Value("${oss.qiniu.accessKey}")
    private String accessKey;

    /**
     * 七牛 SECRET_KEY
     */
    @Value("${oss.qiniu.secretKey}")
    private String secretKey;

    /**
     * 七牛空间名
     */
    @Value("${oss.qiniu.bucketName}")
    private String bucketName;
}
