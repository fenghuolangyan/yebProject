package com.xq.gateway;/*
@author  xq 
@date 2021/4/10 - 13:13 
手握梦想,心向旅行的第10天
三尺代码 一世天涯
*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class yebGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(yebGatewayApplication.class, args);
    }

}
