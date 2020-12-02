package com.junlan.shiro;

import com.junlan.mapper.user.LoginMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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

    private LoginMapper loginMapper;

    @Autowired(required = false)
    public MyRealm(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
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
        // 设置角色/权限信息
        JwtToken jwtToken = (JwtToken) principals.getPrimaryPrincipal();
        // 获取username
//        String username = jwtToken.getUsername();

        String username = JwtUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = loginMapper.getRoleCode(username);
//        // 获得该角色的权限
//        // String rolePermission = userMapper.getRolePermission(username);
//        //获得该用户的权限
//        Set<String> userPermission = permissionMapper.getUserPermission(username);

        Set<String> roleSet = new HashSet<>();
        roleSet.add(role);
//        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
//        info.setStringPermissions(userPermission);
        return info;
    }

    /**
     * 是否登录认证
     *
     * @param aToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken aToken) throws AuthenticationException {
        log.info("验证token：{}", aToken);
        // 校验token
//        JwtToken jwtToken = (JwtToken) aToken;
//        if (jwtToken == null) {
//            throw new AuthenticationException("jwtToken不能为空");
//        }
//        String username = jwtToken.getUsername();

        String token = (String) aToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null || !JwtUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }
        String password = loginMapper.getPassword(username);
        if (password == null) {
            throw new AuthenticationException("该用户不存在！");
        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }


}
