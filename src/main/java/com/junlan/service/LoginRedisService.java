package com.junlan.service;

import com.junlan.model.vo.LoginSysUserTokenVO;
import com.junlan.model.vo.LoginSysUserVO;
import com.junlan.shiro.JwtToken;

/**
 * @Author LJ
 * @Date 2020/12/6
 * msg redis缓存登录信息
 */

public interface LoginRedisService {
    /**
     * 缓存登录信息
     * @param jwtToken
     * @param loginSysUserVO
     */
    void cacheLoginInfo(JwtToken jwtToken, LoginSysUserVO loginSysUserVO);

    /**
     * 获取登录用户信息
     * @param username
     * @return
     */
    LoginSysUserVO getSysUserVO(String username);

}
