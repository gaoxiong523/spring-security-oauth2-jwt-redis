package com.gaoxiong.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

/**
 * @author gaoxiong
 * @ClassName JacksonSerializationStrategy
 * @Description 重写redis 序列化器
 * @date 2019/7/17 8:58
 */
public class JacksonSerializationStrategy implements RedisTokenStoreSerializationStrategy {
    private static final Jackson2JsonRedisSerializer<Object> OBJECT_SERIALIZER = new Jackson2JsonRedisSerializer<>(Object.class);
    private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();

//    @PostConstruct
//    void init(){
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        OBJECT_SERIALIZER.setObjectMapper(om);
//    }

    @Override
    public <T> T deserialize ( byte[] bytes, Class<T> clazz ) {
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        OBJECT_SERIALIZER.setObjectMapper(om);
        System.out.println("反序列化token......................");
        Object deserialize = OBJECT_SERIALIZER.deserialize(bytes);
        return (T) OBJECT_SERIALIZER.deserialize(bytes);
    }

    @Override
    public String deserializeString ( byte[] bytes ) {
        return STRING_SERIALIZER.deserialize(bytes);
    }

    @Override
    public byte[] serialize ( Object object ) {
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        OBJECT_SERIALIZER.setObjectMapper(om);
        return OBJECT_SERIALIZER.serialize(object);
    }

    @Override
    public byte[] serialize ( String data ) {
        return STRING_SERIALIZER.serialize(data);
    }

}
