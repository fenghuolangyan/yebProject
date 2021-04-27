package com.xq.server.utils;/*
@author  xq 
@date 2021/4/14 - 16:14 
手握梦想,心向旅行的第14天
三尺代码 一世天涯
*/

import com.xq.server.exception.CustomerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class dateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if(!StringUtils.isEmpty(source)) {
            try {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
