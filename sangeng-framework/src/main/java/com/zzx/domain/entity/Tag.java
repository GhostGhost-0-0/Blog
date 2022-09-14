package com.zzx.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签(Tag)表实体类
 *
 * @author 那个小楠瓜
 * @since 2022-08-22 16:28:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@TableName("sg_tag")
public class Tag {

    @TableId
    private Long id;
    //标签名
    private String name;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @TableLogic
    private Integer delFlag;
    //备注
    private String remark;

}

