package com.xq;/*
@author  xq 
@date 2021/4/23 - 9:01 
手握梦想,心向旅行的第23天
三尺代码 一世天涯
*/

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class reTest {

    @Test
    public void reflectxx() throws ClassNotFoundException {
        StringBuilder s = new StringBuilder();
        Class<?> c = Class.forName("com.xq.server.controller.AdminController");
        s.append(Modifier.toString(c.getModifiers())+" class "+ c.getSimpleName() +"{\n");
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields)
        {
            s.append("\t");
            s.append(Modifier.toString(field.getModifiers()));
            s.append(" ");
            s.append(field.getType().getSimpleName());
            s.append(" ");
            s.append(field.getName());
            s.append(";\n");
        }
        s.append("}");
        System.out.println(s);
    }
}
