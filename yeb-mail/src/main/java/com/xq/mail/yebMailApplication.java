package com.xq.mail;/*
@author  xq 
@date 2021/4/23 - 9:18 
手握梦想,心向旅行的第23天
三尺代码 一世天涯
*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class yebMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(yebMailApplication.class,args);
    }
}
