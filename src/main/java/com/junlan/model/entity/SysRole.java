package com.junlan.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author LJ
 * @Date 2020/12/3
 * msg 系统角色
 */

@Data   // lombok
@Accessors(chain = true)
@ApiModel(value = "系统sys_role对象", description = "系统角色")
public class SysRole {

    @ApiModelProperty("主键")
    @NotNull(message = "不能为空")
    private Long id;

    @ApiModelProperty(value = "角色唯一编码")
    private String roleCode;

    @ApiModelProperty(value = "角色对应名称")
    private String roleName;
}
