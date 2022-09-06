package com.zzx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-22 16:18
 * @Description: 后台入口
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("com.zzx.domain.mapper")
@EnableSwagger2
@EnableTransactionManagement
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}
