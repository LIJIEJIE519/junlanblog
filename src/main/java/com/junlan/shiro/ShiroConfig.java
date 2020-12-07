package com.junlan.shiro;


import com.junlan.config.properties.JwtProperties;
import com.junlan.config.properties.ShiroProperties;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ShiroConfig
 * @author XJJ
 * @date 2019-09-27
 * 1. 请求门户入口，是ShiroFilter的工厂类
 *
 **/


@Configuration
public class ShiroConfig {

    // JWT过滤器名称
    private static final String JWT_FILTER_NAME = "jwtFilter";

    //Shiro过滤器名称
    private static final String SHIRO_FILTER_NAME = "shiroFilter";

    private static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 先走 filter ，然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
     *
     * 定义主Shiro过滤器。
     */
    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager,
                                          ShiroProperties shiroProperties,
                                          JwtProperties jwtProperties) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 1. 设置安全管理器
        factoryBean.setSecurityManager(securityManager);
        // 2. 设置在创建setFilterChainDefinitionMap筛选器链定义时可用的筛选器，使用构造器注入
        factoryBean.setFilters(getFilterMap(jwtProperties));
        // 3. 设置链定义的链名到链定义映射，以用于创建被Shiro筛选器拦截的筛选器链
        factoryBean.setFilterChainDefinitionMap(getFilterChainDefinitionMap(shiroProperties));

        // 设置无权限时跳转的 url;
        factoryBean.setUnauthorizedUrl("/user/unauthorized");
        return factoryBean;
    }

    /**
     * 获取自定义的JwtFilter
     *
     * @return map
     */
    private Map<String, Filter> getFilterMap(JwtProperties jwtProperties) {
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        /*
        * 通过构造器注入属性，@Autowired在构造器之后，会出现null指针异常
        * */
        filterMap.put(JWT_FILTER_NAME, new JwtFilter(jwtProperties));
        return filterMap;
    }

    /**
     * Shiro 链名到链定义映射
     *
     * @return map
     */
    private Map<String, String> getFilterChainDefinitionMap(ShiroProperties shiroProperties) {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 获取排除的路径
        List<String[]> anonList = shiroProperties.getAnon();
//        log.debug("anonList:{}", JSON.toJSONString(anonList));
        if (CollectionUtils.isNotEmpty(anonList)) {
            anonList.forEach(anons -> {
                if (ArrayUtils.isNotEmpty(anons))
                    for (String anon : anons) {
                        // anon, 匿名拦截器，即不需要登录即可访问
                        filterChainDefinitionMap.put(anon, "anon");
                    }
            });
        }

        // JwtShiro
        List<String[]> permission = shiroProperties.getPermission();
        if (CollectionUtils.isNotEmpty(permission)) {
            permission.forEach(pers -> {
                if (ArrayUtils.isNotEmpty(pers))
                    for (String per : pers) {
                        // anon, 匿名拦截器，即不需要登录即可访问
                        filterChainDefinitionMap.put(per, JWT_FILTER_NAME);
                    }
            });
        }

        // 如果没有启用shiro，全部路径放行
        if (!shiroProperties.isEnable()) {
            filterChainDefinitionMap.put("/**", "anon");
        }
        return filterChainDefinitionMap;
    }


    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 realm.
        securityManager.setRealm(myRealm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
