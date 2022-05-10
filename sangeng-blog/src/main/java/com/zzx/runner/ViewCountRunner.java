package com.zzx.runner;

import com.zzx.domain.entity.Article;
import com.zzx.domain.mapper.ArticleMapper;
import com.zzx.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-05-10 11:47
 **/
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id 浏览量
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),article -> article.getViewCount().intValue()));
        //存储到 redis 中
        redisCache.setCacheMap("article:viewCount", viewCountMap);
        System.out.println("------------把浏览量存到redis中成功-----------");
    }
}
