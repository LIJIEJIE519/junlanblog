package com.junlan.shiro;

import com.junlan.service.LoginService;
import com.junlan.shiro.utils.JwtUtil;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 *
 * @Author LJ
 * @Date 2020/11/28
 * 3. Shiro 登录权限配置
 */

@Component
public class MyRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(MyRealm.class);

    private LoginService loginService;

    public MyRealm(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 仅支持JwtToken类型的Token，即需实现AuthenticationToken
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof JwtToken;
    }

    /**
     * 授权认证,设置角色/权限信息，只有当需要检测用户权限的时候才会调用此方法
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtil.getUsername(principals.toString());
        log.info("验证权限：{}", username);
        Long roleId = loginService.getUserByName(username).getRoleId();
        // 获得该用户角色编码
        String rCode = loginService.getRodeCode(roleId);
        // 获取用户权限编码
        Set<String> pCodes = loginService.getPermissionByRodeId(roleId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(SetUtils.hashSet(rCode));
        info.setStringPermissions(pCodes);
        return info;
    }

    /**
     * token验证，在进行登录
     *
     * @param aToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken aToken) throws AuthenticationException {
        log.info("验证token：{}", aToken);
        String token = (String) aToken.getCredentials();
        if (StringUtils.isBlank(token)) {
            throw new AuthenticationException("token不能为空");
        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
