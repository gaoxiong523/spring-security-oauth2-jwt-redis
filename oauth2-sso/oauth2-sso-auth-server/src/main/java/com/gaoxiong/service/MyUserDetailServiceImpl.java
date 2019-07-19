package com.gaoxiong.service;

import com.gaoxiong.domain.MyUser;
import com.gaoxiong.entity.SysPermission;
import com.gaoxiong.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gaoxiong
 * @ClassName MyUserDetailService
 * @Description TODO
 * @date 2019/7/19 14:20
 */
@Service
@Slf4j
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        SysUser byUsername = userService.getByUsername(username);
        if (byUsername == null) {
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }
        List<SysPermission> permissions = permissionService.findByUserId(byUsername.getId());
        List<String> collect = permissions.stream().map(p -> p.getName()).collect(Collectors.toList());
        String[] strings = collect.toArray(new String[collect.size()]);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(strings);

        MyUser myUser = new MyUser(byUsername.getUsername(), passwordEncoder.encode(byUsername.getPassword()), authorities);
        log.info("登陆用户是{}", myUser);
        return myUser;
    }
}
