package com.zzx.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.dto
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-05 13:30
 * @Description: 用户列表 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户列表 dto")
public class UserListDto {

    @ApiModelProperty(notes = "用户编号")
    private Long id;

    @ApiModelProperty(notes = "用户名")
    private String userName;

    @ApiModelProperty(notes = "用户昵称")
    private String nickName;

    @ApiModelProperty(notes = "账号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(notes = "手机号")
    private String phonenumber;

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
}
