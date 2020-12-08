package com.junlan.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author LJ
 * @Date 2020/12/2
 * msg
 */
@Data
@Accessors(chain = true)
public class LoginSysUserVO implements Serializable {

    private static final long serialVersionUID = -6987256913978663358L;

    private Long id;

    private String username;

    private String usernick;

    private Long roleId;

    private String roleCode;

    private String roleName;

    private Set<String> permissionCodes;
}
