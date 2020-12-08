package com.junlan.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author LJ
 * @Date 2020/12/3
 * msg 系统权限类
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "Sys_Permission对象")
public class SysPermission {

    @ApiModelProperty("主键")
    @NotNull(message = "不能为空")
    private Long id;

    @ApiModelProperty(value = "归属类型编码")
    private String typeCode;

    @ApiModelProperty(value = "归属类型名称")
    private String typeName;

    @ApiModelProperty(value = "权限对应编码")
    private String permissionCode;

    @ApiModelProperty(value = "权限对应名称")
    private String permissionName;
}
