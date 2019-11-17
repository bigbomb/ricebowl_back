package com.shiro.steel.config;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/** 

* @author 作者 lujunjie: 

* @version 创建时间：Nov 17, 2019 12:50:28 PM 

* 类说明 

*/
@Configuration
@EnableCaching  //继承CachingConfigurerSupport并重写方法，配合该注解实现spring缓存框架的使用
public class RedisConfig extends CachingConfigurerSupport {
    /**载入配置文件配置的连接工厂**/
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    /*不提示警告信息*/
    @SuppressWarnings("rawtypes")
    @Autowired
    RedisTemplate redisTemplate;
 
    @Bean
    RedisTemplate<String,Object> objectRedisTemplate(){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
 
    @Bean 
    @Override
    public CacheManager cacheManager(){
        RedisCacheManager redisCacheManager=new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
//        redisCacheManager.setDefaultExpiration(60);//秒
        return redisCacheManager;
    }
 
    /**
     * 重写缓存key生成策略，可根据自身业务需要进行自己的配置生成条件
     * @return
     */
    @Bean 
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
 
}

