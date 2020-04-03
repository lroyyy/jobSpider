package com.getfei.jobSpider.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.getfei.jobSpider.realm.UserRealm;

@ConfigurationProperties(prefix = "shiro")
@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }
    /**
      *  配置url
      *  anon 任何人都能访问
      *  authc 认证成功后才能访问
      */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        Map<String,String> pathDefinitions = new HashMap<>();
//        pathDefinitions.put("/*","anon");
        pathDefinitions.put("/deliverRecord*","authc");
        chain.addPathDefinitions(pathDefinitions);
        return chain;
    }


    /**
     * 密码验证
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(6);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

}