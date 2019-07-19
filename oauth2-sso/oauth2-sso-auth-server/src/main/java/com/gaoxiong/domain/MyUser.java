package com.gaoxiong.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author gaoxiong
 * @ClassName MyUser
 * @Description 大部分时候直接用User即可不必扩展
 * @date 2019/7/19 17:02
 */
@Data
public class MyUser extends User {

    //// 举个例子，假设我们想增加一个字段，这里我们增加一个mobile表示手机号
    private String mobile;

    //举个例子,部门id
    private Integer departmentId;


    public MyUser ( String username, String password, Collection<? extends GrantedAuthority> authorities ) {
        super(username, password, authorities);
    }

    public MyUser ( String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
