package com.goushuang.lyz.configuragion;

import net.spy.memcached.spring.MemcachedClientFactoryBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemcacheConfig extends CachingConfigurerSupport{

    @Bean
    public MemcachedClientFactoryBean getMemcachedClientFactoryBean(){
        MemcachedClientFactoryBean memcachedClientFactoryBean = new MemcachedClientFactoryBean();
        memcachedClientFactoryBean.setServers("127.0.0.1:11211");
        return memcachedClientFactoryBean;
    }
}
