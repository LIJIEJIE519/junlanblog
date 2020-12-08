package com.junlan.service.serviceImp;

import com.junlan.config.properties.JwtProperties;
import com.junlan.model.vo.LoginSysUserVO;
import com.junlan.service.LoginRedisService;
import com.junlan.shiro.JwtToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author LJ
 * @Date 2020/12/6
 * msg  Redis 登录信息缓存
 */

@Service
public class LoginRedisServiceImp implements LoginRedisService {

    private final static String RedisTokenPrefix = "JwtToken-";

    private final Logger logger = LoggerFactory.getLogger(LoginRedisServiceImp.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public void cacheLoginInfo(JwtToken jwtToken, LoginSysUserVO loginSysUserVO) {
        if (jwtToken == null) {
            throw new IllegalArgumentException("jwtToken不能为空");
        }
        if (loginSysUserVO == null) {
            throw new IllegalArgumentException("loginSysUserVo不能为空");
        }

        String username = loginSysUserVO.getUsername();
        logger.debug("redis缓存->username:{}, jToken:{}", username, jwtToken);
        // 缓存登录信息到redis
        // 必须加上TimeUnit.SECONDS参数，过期时间形式
        redisTemplate.opsForValue().set(username, loginSysUserVO, jwtProperties.getExpireSecond(), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(RedisTokenPrefix + username, jwtToken, jwtProperties.getExpireSecond(), TimeUnit.SECONDS);

        logger.debug("LoginSysUserVO:{}", redisTemplate.opsForValue().get(username));
        logger.debug("JwtToken:{}", redisTemplate.opsForValue().get(RedisTokenPrefix + username));
    }

    @Override
    public LoginSysUserVO getSysUserVO(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username不能为空");
        }
        logger.info("getSysUserVO:{}", redisTemplate.opsForValue().get(username));
        return (LoginSysUserVO) redisTemplate.opsForValue().get(username);
    }

    @Override
    public void deleteLoginInfo(String username) {
        if (username == null) {
            throw new IllegalArgumentException("username不能为空");
        }

        redisTemplate.delete(username);
        redisTemplate.delete(RedisTokenPrefix + username);
    }

    /**
     * 修改键默认序列化方式
     * @param redisTemplate
     */
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }
}
