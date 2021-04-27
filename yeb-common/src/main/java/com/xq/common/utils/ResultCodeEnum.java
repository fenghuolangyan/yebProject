package com.xq.common.utils;/*
@author  xq 
@date 2021/3/8 - 9:28 
手握梦想,心向旅行的第08天
三尺代码 一世天涯
*/

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResultCodeEnum {

    SUCCESS(20000, "成功"),
    UNKNOWN_REASON(20001, "未知错误"),

    BAD_SQL_GRAMMAR(20002, "sql语法错误"),
    UPDAET_SQL_GRAMMAR(20003, "更新失败"),
    DELETE_SQL_GRAMMAR(20004, "删除失败"),

    JSON_PARSE_ERROR(21010, "json解析异常"),
    PARAM_ERROR(21013, "参数不正确");
    private Integer code;
    private String Message;

    ResultCodeEnum(Integer code, String Message) {
        this.code = code;
        this.Message = Message;
    }
}
