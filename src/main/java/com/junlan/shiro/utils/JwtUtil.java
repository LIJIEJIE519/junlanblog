package com.junlan.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.junlan.config.properties.JwtProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author LJ
 * @Date 2020/11/27
 * msg JWT 生成token配置类
 */

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private static JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        JwtUtil.jwtProperties = jwtProperties;
    }

    /**
     * 生成 token
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String createToken(String username) {
        try {
            // 过期时间
            Date expire = new Date(System.currentTimeMillis() + jwtProperties.getExpireSecond());
            // 生成算法
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    // 签发人
                    .withIssuer(jwtProperties.getIssuer())
                    // 签发的目标
                    .withAudience(jwtProperties.getAudience())
                    //到期时间
                    .withExpiresAt(expire)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验 token 是否正确
     *
     * @param token
     * @param username
     * @return
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取过期时间
s     */
    public static Date getExpiresAt(String token) {
        DecodedJWT decodedJwt = JWT.decode(token);
        if (decodedJwt == null) {
            return null;
        }
        return decodedJwt.getExpiresAt();
    }

    /**
     * 判断token是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpired(String token) {
        Date expireDate = getExpiresAt(token);
        if (expireDate == null) {
            return true;
        }
        // 期长与当前时间比较判断
        return !expireDate.before(new Date());
    }


    /**
     * 从请求参数 或 请求头中获得token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request不能为空");
        }
        //  请求头
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            // 请求参数
            token = request.getParameter("token");
        }
        return token;
    }
}
