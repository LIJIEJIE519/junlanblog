package com.junlan.model.user;

import lombok.Data;

/**
 * @Author LJ
 * @Date 2020/11/26
 * msg
 */

@Data
public class User {
    private Long id;
    private String username;
    private String usernick;
    private String password;
    private Long roleId;
    private String salt;

}
