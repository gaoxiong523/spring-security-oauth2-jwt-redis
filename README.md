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