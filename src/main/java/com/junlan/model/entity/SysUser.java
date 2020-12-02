package com.junlan.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author LJ
 * @Date 2020/11/26
 * msg
 */

@Data
@Accessors(chain = true)
public class SysUser implements Serializable {

    private Long id;
    private String username;
    private String usernick;
    private String password;
    private Long roleId;
    private String salt;

}
