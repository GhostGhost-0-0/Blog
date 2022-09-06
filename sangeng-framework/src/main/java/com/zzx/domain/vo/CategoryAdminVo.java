package com.zzx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.vo
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-31 19:37
 * @Description: 分类 vo
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAdminVo {

    private Long id;
    private String name;
    private String description;
    private String status;
}
