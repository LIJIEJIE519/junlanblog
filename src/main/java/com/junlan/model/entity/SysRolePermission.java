package com.junlan.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author LJ
 * @Date 2020/12/3
 * msg
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "Sys_Role_Permission对象", description = "一个角色对应多个权限")
public class SysRolePermission {

    @ApiModelProperty("主键")
    @NotNull(message = "不能为空")
    private Long id;

    @ApiModelProperty("角色ID")
    @NotNull(message = "不能为空")
    private Long RoleId;

    @ApiModelProperty("权限ID")
    @NotNull(message = "不能为空")
    private Long PermissionId;
}
