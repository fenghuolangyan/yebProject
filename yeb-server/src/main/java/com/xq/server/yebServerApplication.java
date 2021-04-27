package com.xq.server;/*
@author  xq 
@date 2021/4/10 - 12:32 
手握梦想,心向旅行的第10天
三尺代码 一世天涯
*/

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.xq"})
@MapperScan("com.xq.server.mapper")
public class yebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(yebServerApplication.class,args);
    }
}
