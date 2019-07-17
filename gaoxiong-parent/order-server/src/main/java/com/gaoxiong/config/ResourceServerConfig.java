package com.gaoxiong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author gaoxiong
 * @ClassName ResourceServerConfig
 * @Description TODO
 * @date 2019/7/17 11:04
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    @CrossOrigin
    public void configure ( ResourceServerSecurityConfigurer resources ) throws Exception {
        resources.tokenServices(resourceServerTokenServices());
    }

    @Override
    public void configure ( HttpSecurity http ) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/order/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()
                .authenticated();

    }

    @Bean
    public ResourceServerTokenServices resourceServerTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        return tokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter () {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //指定签名,生产中使用RSA 非对称签名
        converter.setSigningKey("testKey");
        return converter;
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory () {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory("106.12.84.126", 6379);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }
    @Bean
    public JacksonSerializationStrategy jacksonSerializationStrategy(){
        return new JacksonSerializationStrategy();
    }

    @Bean
    public TokenStore tokenStore () {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory());
        //序列化
        redisTokenStore.setSerializationStrategy(jacksonSerializationStrategy());
        return redisTokenStore;
    }

}
