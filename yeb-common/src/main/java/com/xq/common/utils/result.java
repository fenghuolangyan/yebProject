package com.xq.common.utils;/*
@author  xq 
@date 2021/4/13 - 11:11 
手握梦想,心向旅行的第13天
三尺代码 一世天涯
*/

public class result {
    public static RespBean respBean(boolean b){
        if(b){
            return RespBean.success("操作成功");
        }
        return RespBean.error("操作失败");
    }
}
