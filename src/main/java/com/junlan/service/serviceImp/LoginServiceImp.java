package com.junlan.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junlan.mapper.user.SysUserMapper;
import com.junlan.model.vo.LoginSysUserTokenVO;
import com.junlan.model.entity.SysUser;
import com.junlan.model.vo.LoginSysUserVO;
import com.junlan.service.LoginService;
import com.junlan.shiro.JwtUtil;
import com.junlan.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author LJ
 * @Date 2020/12/2
 * msg
 */

@Slf4j
@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public LoginSysUserTokenVO login(String username, String password) {
        SysUser sysUser = getUserByName(username);
        if (sysUser == null) {
            log.error("无该用户：{}", username);
            throw new AuthenticationException("用户名错误");
        }
        String encrypt = UserUtil.encrypt(password, sysUser.getSalt());
        if (!sysUser.getPassword().equals(encrypt)) {
            throw new AuthenticationException("用户名或密码错误");
        }

        // 系统对象转登录对象VO
        LoginSysUserVO loginSysUserVO = new LoginSysUserVO()
                .setId(sysUser.getId()).setUsername(sysUser.getUsername())
                .setUsernick(sysUser.getUsernick()).setRoleId(sysUser.getRoleId());


        // 生成token
        String token = JwtUtil.createToken(username);
        log.debug("token：{}", token);

        // 返回token和登录用户信息对象
        LoginSysUserTokenVO loginSysUserTokenVo = new LoginSysUserTokenVO()
                .setToken(token)
                .setLoginSysUserVO(loginSysUserVO);
        return loginSysUserTokenVo;
    }

    @Override
    public void logout(HttpServletRequest request) {

    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    public SysUser getUserByName(String username) {
//        return sysUserMapper.selectOne(new QueryWrapper<SysUser>(new SysUser().setUsername(username)));
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda().eq(SysUser::getUsername, username));
    }

}
