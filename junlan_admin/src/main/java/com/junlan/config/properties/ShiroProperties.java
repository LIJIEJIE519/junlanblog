package com.junlan.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author LJ
 * @Date 2020/12/1
 * msg Shiro 配置映射类，配置yaml的
 */

@Data
@ConfigurationProperties(value = "shiro")
@Component
public class ShiroProperties {

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 路径权限配置, xxx-xxx松散绑定
     */
    private String filterChainDefinitions;

    /**
     * 设置无需权限路径集合
     */
    private List<String[]> anon;

    /*
    * 设置通过JwtShiro
    * */
    private List<String[]> permission;


}
