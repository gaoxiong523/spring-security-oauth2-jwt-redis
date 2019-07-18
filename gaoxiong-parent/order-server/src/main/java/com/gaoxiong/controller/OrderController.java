package com.gaoxiong.controller;

import com.alibaba.fastjson.JSON;
import com.gaoxiong.pojo.User;
import com.gaoxiong.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaoxiong
 * @ClassName OrderController
 * @Description TODO
 * @date 2019/7/17 11:17
 */
@RestController
@Slf4j
public class OrderController {

    @GetMapping("/product/{id}")
    public String getProduct( @PathVariable String id, HttpServletRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("----"+authentication);
        System.out.println("用户名  : " + JSON.toJSONString(authentication.getPrincipal()));
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getUsername());
        System.out.println("封装的传递信息  : " + AuthUtils.getReqUser(req));
        return "(Need Auth Request)product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        return "(No Auth Request)order id : " + id;
    }


}
