package com.zzx.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-08-30 14:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "友链 dto")
public class LinkListDto {

    @ApiModelProperty(notes = "主键 id")
    private Long id;

    @ApiModelProperty(notes = "友链名称")
    @NotBlank(message = "友链名称不能为空")
    private String name;

    @ApiModelProperty(notes = "友链的图标")
    @NotBlank(message = "友链图标不能为空")
    private String logo;

    @ApiModelProperty(notes = "审核状态：0-审核通过，1-审核未通过，2-未审核")
    private String status;

    @ApiModelProperty(notes = "友链的地址")
    @NotBlank(message = "友链地址不能为空")
    @URL
    private String address;

    @ApiModelProperty(notes = "友链的描述")
    private String description;

}
