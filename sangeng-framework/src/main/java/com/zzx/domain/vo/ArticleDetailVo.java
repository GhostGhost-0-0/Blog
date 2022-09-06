package com.zzx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 13:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {

    //标题
    private String title;
    //文章内容
    private String content;
    //摘要
    private String summary;
    //缩略图
    private String thumbnail;
    //所属分类id
    private Long categoryId;
    //分类名称
    private String categoryName;
    //标签
    private List<Long> tags;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;
    //是否置顶
    private String isTop;
    private Date createTime;


}
