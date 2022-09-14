package com.zzx.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (ArticleTag)表实体类
 *
 * @author 那个小楠瓜
 * @since 2022-09-01 14:56:14
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_article_tag")
public class ArticleTag {
    //博文id
    private Long articleId;
    //标签id
    private Long tagId;


}
