package com.zzx.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    //所属分类id
    private Long categoryId;
    //分类名称
    @TableField(exist = false)
    private String categoryName;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;

    private Date createTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}
