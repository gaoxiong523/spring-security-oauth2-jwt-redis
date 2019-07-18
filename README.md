# spring-security-oauth2-jwt-redis

##关于resource_ids
```text
Spring Security OAuth2 架构上分为Authorization Server和Resource Server。
我们可以为每一个Resource Server（一个微服务实例）设置一个resourceid。
再给client授权的时候，可以设置这个client可以访问哪一些微服务实例，
如果没设置，就是对所有的resource都有访问权限。

在Spring Security的FilterChain中，OAuth2AuthenticationProcessingFilter在
FilterSecurityInterceptor的前面，所以会先验证client有没有此resource的权限，
只有在有此resource的权限的情况下，才会再去做进一步的判断；

```

##关于jackson反序列化时的报错
```java
Caused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Unexpected token (START_OBJECT), expected START_ARRAY: need JSON Array to contain As.WRAPPER_ARRAY type information for class java.lang.Object
 at [Source: (byte[])"{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjM1MjMyMzYsInVzZXJfbmFtZSI6Imdhb3hpb25nIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9NRURJVU0iLCJST0xFX05PUk1BTCIsIlJPTEVfQURNSU4iXSwianRpIjoiZDk0ODQ4YWYtYTE4Zi00OTE5LWFkMmYtYzZiNGQ4NmZkOWRiIiwiY2xpZW50X2lkIjoibXlhcHAiLCJzY29wZSI6WyJhbGwiXX0.2ZdpB6ZIjU_lEbzxCqhurd2TEqxgVHaLGzB1VWT6qFk","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJnYW94aW9uZyIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiJkOTQ4NDhhZi1hMThmLTQ5M"[truncated 338 bytes]; line: 1, column: 1]
	at com.fasterxml.jackson.databind.exc.MismatchedInputException.from(MismatchedInputException.java:59) ~[jackson-databind-2.9.9.jar:2.9.9]
	at com.fasterxml.jackson.databind.DeserializationContext.wrongTokenException(DeserializationContext.java:1499) ~[jackson-databind-2.9.9.jar:2.9.9]
	at com.fasterxml.jackson.databind.DeserializationContext.reportWrongTokenException(DeserializationContext.java:1274) ~[jackson-databind-2.9.9.jar:2.9.9]
	at com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer._locateTypeId(AsArrayTypeDeserializer.java:137) ~[jackson-databind-2.9.9.jar:2.9.9]
	at com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer._deserialize(AsArrayTypeDeserializer.java:96) ~[jackson-databind-2.9.9.jar:2.9.9]
	at com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer.deserializeTypedFromAny(AsArrayTypeDeserializer.java:71) ~[jackson-databind-2.9.9.jar:2.9.9]
	at com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer$Vanilla.deserializeWithType(UntypedObjectDeserializer.java:712) ~[jackson-databind-2.9.9.jar:2.9.9]
	at com.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer.deserialize(TypeWrappedDeserializer.java:68) ~[jackson-databind-2.9.9.jar:2.9.9]
	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4013) ~[jackson-databind-2.9.9.jar:2.9.9]
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3129) ~[jackson-databind-2.9.9.jar:2.9.9]
	at org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer.deserialize(Jackson2JsonRedisSerializer.java:73) ~[spring-data-redis-2.1.9.RELEASE.jar:2.1.9.RELEASE]
	... 41 common frames omitted
```

解决方法如下
```java
 ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //将下面这行注释掉即可
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        OBJECT_SERIALIZER.setObjectMapper(om);
```
