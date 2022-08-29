package com.zzx.domain.vo;

import com.zzx.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.vo
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-29 17:28
 * @Description: 路由 vo
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<Menu> menus;
}
