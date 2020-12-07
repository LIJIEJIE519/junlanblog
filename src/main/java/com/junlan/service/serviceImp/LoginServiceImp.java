package com.junlan.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junlan.config.properties.JwtProperties;
import com.junlan.mapper.SysUserMapper;
import com.junlan.model.entity.SysRole;
import com.junlan.model.vo.LoginSysUserTokenVO;
import com.junlan.model.entity.SysUser;
import com.junlan.model.vo.LoginSysUserVO;
import com.junlan.service.LoginRedisService;
import com.junlan.service.LoginService;
import com.junlan.service.SysRolePermissionService;
import com.junlan.service.SysRoleService;
import com.junlan.shiro.JwtToken;
import com.junlan.shiro.utils.JwtUtil;
import com.junlan.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @Author LJ
 * @Date 2020/12/2
 * msg  登录信息
 */

@Slf4j
@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private LoginRedisService loginRedisService;

    // 获取秘药-过期属性
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public LoginSysUserTokenVO login(String username, String password) throws Exception{
        SysUser sysUser = getUserByName(username);
        if (sysUser == null) {
            log.error("无该用户：{}", username);
            throw new AuthenticationException("用户名错误");
        }
        String encrypt = UserUtil.encrypt(password, sysUser.getSalt());
        if (!sysUser.getPassword().equals(encrypt)) {
            throw new AuthenticationException("用户名或密码错误");
        }

        // 系统对象 转 登录对象VO
        LoginSysUserVO loginSysUserVO = new LoginSysUserVO()
                .setId(sysUser.getId()).setUsername(sysUser.getUsername())
                .setUsernick(sysUser.getUsernick()).setRoleId(sysUser.getRoleId());

        // 获取登录对象角色并设置
        SysRole sysRole = sysRoleService.getById(sysUser.getRoleId());
        loginSysUserVO.setRoleId(sysRole.getId())
                .setRoleCode(sysRole.getRoleCode())
                .setRoleName(sysRole.getRoleName());

        // 获取登录对象权限编码并设置
        Set<String> sysCodes = sysRolePermissionService.getPermissionCodesByRoleId(sysUser.getRoleId());
        loginSysUserVO.setPermissionCodes(sysCodes);

        // 生成token
        String token = JwtUtil.createToken(username);
        JwtToken jwtToken = JwtToken.build(username, token,
                jwtProperties.getSecret(), jwtProperties.getExpireSecond());

        // redis缓存信息
        loginRedisService.cacheLoginInfo(jwtToken, loginSysUserVO);

        // 返回token和登录用户信息对象
        return new LoginSysUserTokenVO()
                .setToken(token)
                .setLoginSysUserVO(loginSysUserVO);
    }

    @Override
    public void logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        String token = JwtUtil.getToken(request);
        final String username = JwtUtil.getUsername(token);
        log.info("登出成功，username: {}", username);
    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public SysUser getUserByName(String username) {
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda().eq(SysUser::getUsername, username));
    }

    @Override
    public String getRodeCode(Long roleId) {
        return sysRoleService.getById(roleId).getRoleCode();
    }

    @Override
    public Set<String> getPermissionByRodeId(Long roleId) {
        return sysRolePermissionService.getPermissionCodesByRoleId(roleId);
    }
}
