package com.zzx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: SGBlog
 * @Description 分页 vo
 * @Author: 那个小楠瓜
 * @create: 2022-02-23 19:37
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    private List rows;
    private Long total;
}
