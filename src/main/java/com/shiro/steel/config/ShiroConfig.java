package com.shiro.steel.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shiro.steel.shiro.AuthRealm;
import com.shiro.steel.shiro.CredentialsMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

/**
 * @desc: Shiro配置
 *
 * @author: jwy
 * @date: 2017/12/16
 */
@Configuration
public class ShiroConfig {

    /**
     * 日志打印
     */
    private final static Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        log.info("====================== shrio拦截器工厂注入开始 =====================");

        ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        sffb.setSecurityManager(securityManager);
        Map<String, Filter> newfilterMap = sffb.getFilters();
        sffb.setFilters(newfilterMap);
//        // 登录页面
//        sffb.setLoginUrl("/LoginApi/v1/relogin");
//        // 登录成功后要跳转的链接
//        // 未授权界面;
//        sffb.setUnauthorizedUrl("/LoginApi/v1/relogin");
        
        // 登录页面
        sffb.setLoginUrl("http://steeladmin.huixiniron.com:8080/api/LoginApi/v1/relogin");
        // 登录成功后要跳转的链接
        // 未授权界面;
        sffb.setUnauthorizedUrl("http://steeladmin.huixiniron.com:8080/api/LoginApi/v1/relogin");
        // 拦截器
        Map<String,String> filterMap = new LinkedHashMap<>();
        // 可以匿名访问
        filterMap.put("/", "anon");
        filterMap.put("/LoginApi/v1/login", "anon");
        filterMap.put("/LoginApi/v1/relogin", "anon");
        filterMap.put("/LoginApi/v1/logOut", "anon");
        filterMap.put("/UserApi/v1/uploadHander", "anon");
        filterMap.put("/TestApi/v1/test","anon");

        // 需要认证才可以访问
        filterMap.put("/**", "authc");
      
        sffb.setFilterChainDefinitionMap(filterMap);

        log.info("====================== shrio拦截器工厂注入成功 =====================");
        return sffb;
    }

    /**
     * @desc: 配置核心安全事务管理
     *
     * @date: 2017/12/18
     */
    @Bean
    public SecurityManager securityManager() {
        log.info("========================shiro已经加载======================");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     *  配置自定义的权限登录器
     *
     * @return
     */
    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(credentialsMatcher());
        return authRealm;
    }

    //配置自定义的密码比较器
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(7200000);
        return sessionManager;
    }
    
    
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }
}
