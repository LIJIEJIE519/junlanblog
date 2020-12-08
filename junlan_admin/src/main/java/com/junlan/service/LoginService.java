package com.junlan.service;

import com.junlan.model.entity.SysUser;
import com.junlan.model.vo.LoginSysUserTokenVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @Author LJ
 * @Date 2020/12/2
 * msg
 */

public interface LoginService {


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    LoginSysUserTokenVO login(String username, String password) throws Exception;

    /**
     * 登出
     * @param request
     */
    void logout(HttpServletRequest request);

    /**
     * 根据用户名获取系统用户
     * @param username
     * @return
     */
    SysUser getUserByName(String username);

    /**
     * 根据role_id获取角色编码
     * @param roleId
     * @return
     */
    String getRodeCode(Long roleId);

    /**
     * 根据rode_id获取权限编码
     * @param roleId
     * @return
     */
    Set<String> getPermissionByRodeId(Long roleId);

}
