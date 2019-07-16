package com.gaoxiong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author gaoxiong
 * @ClassName AuthServerApplicaion
 * @Description TODO
 * @date 2019/7/16 16:31
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class AuthServerApplicaion {
    public static void main ( String[] args ) {
        SpringApplication.run(AuthServerApplicaion.class, args);
    }
}
