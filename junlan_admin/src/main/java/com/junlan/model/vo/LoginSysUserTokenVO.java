package com.junlan.model.vo;

import com.junlan.model.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author LJ
 * @Date 2020/12/2
 * msg
 */

@Data
@ApiModel("登录用户信息TokenVO")
@Accessors(chain = true)
public class LoginSysUserTokenVO implements Serializable {

    private static final long serialVersionUID = -2824899592488101374L;

    @ApiModelProperty("token")
    private String token;

    private LoginSysUserVO loginSysUserVO;
}
