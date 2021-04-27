package com.xq.server.utils;/*
@author  xq 
@date 2021/4/14 - 14:53 
手握梦想,心向旅行的第14天
三尺代码 一世天涯
*/

import com.xq.server.entity.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminUtil {
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
