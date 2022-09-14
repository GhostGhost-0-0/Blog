package com.zzx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: SGBlog
 * @Description 前台入口
 * @Author: 那个小楠瓜
 * @create: 2022-02-23 13:32
 **/
@SpringBootApplication
@MapperScan("com.zzx.domain.mapper")
@EnableSwagger2
public class SanGengBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanGengBlogApplication.class, args);
    }
}
