package com.junlan.shiro;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.HostAuthenticationToken;

import java.util.Date;

/**
 * 4. 收集用户提交的身份
 * Shiro JwtToken对象
 *
 * @author XJJ
 * @date 2019-09-27
 *
 **/
@Data
@Accessors(chain = true)
//public class JwtToken implements HostAuthenticationToken {
public class JwtToken implements AuthenticationToken {

	// 登录ip
//    private String host;

//    private String username;
//
//    private String salt;

    private String token;

//    private Date createDate;
//
//    // 多长时间过期，默认一小时
//    private long expireSecond;
    /**
     * 过期日期
     */
//    private Date expireDate;

//    private String principal;

//    private String credentials;

    public JwtToken() {}

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

//    public static JwtToken build(String token, String username, String salt, long expireSecond) {
//        return new JwtToken()
//                .setUsername(username)
//                .setToken(token)
//                .setSalt(salt);
//
//    }

}
