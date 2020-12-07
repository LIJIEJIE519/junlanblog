package com.junlan.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author LJ
 * @Date 2020/12/6
 * msg Shiro 配置映射类，配置YAML的
 */

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {

    /**
     * token请求头名称
     */
    private String tokenName;

    /**
     * 秘药
     */
    private String secret;

    /**
     * 发行人
     */
    private String issuer;

    private String audience;

    /**
     * 默认过期时间1小时，单位：秒s
     */
    private Long expireSecond;
}
