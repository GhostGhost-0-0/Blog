package com.zzx.domain.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.entity.excel
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-31 22:45
 * @Description: 分类的 Excel 实体
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode
public class CategoryExcel {

    @ExcelProperty(value = "分类编号", index = 0)
    private Long id;

    @ExcelProperty(value = "分类名", index = 1)
    private String name;

    @ExcelProperty(value = "分类描述", index = 2)
    private String description;

    @ExcelProperty(value = "分类状态", index = 3)
    private String status;

    @ExcelProperty(value = "创建人", index = 4)
    private Long createBy;

    @ExcelProperty(value = "创建时间", index = 5)
    private Date createTime;

    @ExcelProperty(value = "更新人", index = 6)
    private Long updateBy;

    @ExcelProperty(value = "更新时间", index = 7)
    private Date updateTime;

    @ExcelProperty(value = "删除标记", index = 8)
    private Integer delFlag;

}
