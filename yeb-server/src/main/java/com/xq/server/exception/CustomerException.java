package com.xq.server.exception;/*
@author  xq 
@date 2021/4/13 - 10:12 
手握梦想,心向旅行的第13天
三尺代码 一世天涯
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerException extends RuntimeException{
    private Integer code;
    private String msg;

    @Override
    public String toString() {
        return "CustomerException{" +
                "message=" + this.getMsg() +
                ", code=" + code +
                '}';
    }
}