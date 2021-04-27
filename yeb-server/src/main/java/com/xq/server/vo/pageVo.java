package com.xq.server.vo;/*
@author  xq 
@date 2021/4/14 - 16:11 
手握梦想,心向旅行的第14天
三尺代码 一世天涯
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.time.Period;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class pageVo {
    /*
     * 总条数
     */
    private Long total;
    /*
     * 数据list
     */
    private List<?> data;
}
