package com.gaoxiong.service;

import com.gaoxiong.config.JacksonSerializationStrategy;
import com.gaoxiong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;

/**
 * @author gaoxiong
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @date 2019/7/11 0011 下午 10:11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public static void main ( String[] args ) {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory("106.12.84.126", 6379);
        connectionFactory.afterPropertiesSet();
        RedisConnection connection = connectionFactory.getConnection();
        JacksonSerializationStrategy jdkSerializationStrategy = new JacksonSerializationStrategy();
         String key = "access:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjM0MzIwOTgsInVzZXJfbmFtZSI6Imdhb3hpb25nIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9NRURJVU0iLCJST0xFX05PUk1BTCIsIlJPTEVfQURNSU4iXSwianRpIjoiYzIzZjVjZWEtODZlOS00Yzc5LWJjOGQtOWQzNjU1Zjg2ZTY0IiwiY2xpZW50X2lkIjoibXlhcHAiLCJzY29wZSI6WyJhbGwiXX0.bIPCC9oNhyXl7I8cXdITrxqtET_gesWpmmL-Q5g9WtE";
        byte[] keybytes = jdkSerializationStrategy.serialize(key);
//        byte[] valuebytes = jdkSerializationStrategy.serialize(value);
//        connection.set(keybytes, valuebytes);
        byte[] bytes = connection.get(keybytes);
        String s = jdkSerializationStrategy.deserializeString(bytes);
        System.out.println("s = " + s);
    }
}
