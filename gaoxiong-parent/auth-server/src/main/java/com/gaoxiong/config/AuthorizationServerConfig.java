package com.gaoxiong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author gaoxiong
 * @ClassName AuthorizationServerConfig
 * @Description TODO
 * @date 2019/7/16 16:33
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // 资源ID
    private static final String SOURCE_ID = "order";
    private static final int ACCESS_TOKEN_TIMER = 60 * 60 * 24;
    private static final int REFRESH_TOKEN_TIMER = 60 * 60 * 24 * 30;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public RedisConnectionFactory redisConnectionFactory () {
        LettuceConnectionFactory factory = new LettuceConnectionFactory("106.12.84.126", 6379);
        factory.afterPropertiesSet();
        return factory;
    }

    @Override
    public void configure ( ClientDetailsServiceConfigurer clients ) throws Exception {
        clients.inMemory()
                .withClient("myapp")
                .secret(new BCryptPasswordEncoder().encode("lxapp"))
                //不设置 resourceids 就是对所有resource都有访问权限
//                .resourceIds(SOURCE_ID)
                .authorizedGrantTypes("Authorization Code", "password", "refresh_token")
                .scopes("all")
                .accessTokenValiditySeconds(ACCESS_TOKEN_TIMER)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_TIMER)
                .and()
                .withClient("zuul-server")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("Authorization Code", "password", "refresh_token")
                .scopes("all")
                .accessTokenValiditySeconds(ACCESS_TOKEN_TIMER)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_TIMER);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter () {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //指定签名,生产中使用RSA 非对称签名
        converter.setSigningKey("testKey");
        return converter;
    }



    @Bean
    public TokenStore tokenStore () {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory());
        return redisTokenStore;
    }

    @Override
    public void configure ( AuthorizationServerEndpointsConfigurer endpoints ) throws Exception {
        endpoints.tokenEnhancer(jwtAccessTokenConverter());
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore());
    }

    @Override
    public void configure ( AuthorizationServerSecurityConfigurer security ) throws Exception {
        //允许表单认证
        security.allowFormAuthenticationForClients();
    }
}
