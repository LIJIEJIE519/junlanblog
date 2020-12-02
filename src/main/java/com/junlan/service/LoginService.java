package com.junlan.service;

import com.junlan.model.vo.LoginSysUserTokenVO;

import javax.servlet.http.HttpServletRequest;

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
    LoginSysUserTokenVO login(String username, String password);

    /**
     * 登出
     * @param request
     */
    void logout(HttpServletRequest request);

}
