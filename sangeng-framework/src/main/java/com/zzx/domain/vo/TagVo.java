package com.zzx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.vo
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-29 21:27
 * @Description: 标签 vo
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private Long id;
    private String name;
    private String remark;
}
