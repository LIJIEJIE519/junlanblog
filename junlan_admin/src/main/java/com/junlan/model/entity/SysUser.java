package com.junlan.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.junlan.common.validator.group.Add;
import com.junlan.common.validator.group.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author LJ
 * @Date 2020/11/26
 * msg
 */

@Data
@Accessors(chain = true)
@ApiModel
public class SysUser implements Serializable {

    private static final long serialVersionUID = 4407481892786292931L;

    @ApiModelProperty("主键")
    @NotNull(message = "id", groups = {Update.class})
    @TableId(value = "id", type = IdType.AUTO)      // mybatis-plus,id自增
    private Long id;

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不为null", groups = {Add.class})
    private String username;

    private String usernick;

    private String password;

    private Long roleId;

    private String salt;

}
