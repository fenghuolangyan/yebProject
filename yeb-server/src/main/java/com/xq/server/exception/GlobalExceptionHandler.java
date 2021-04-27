package com.xq.server.exception;/*
@author  xq 
@date 2021/4/13 - 10:07 
手握梦想,心向旅行的第13天
三尺代码 一世天涯
*/

import com.baomidou.mybatisplus.extension.api.R;
import com.xq.common.utils.RespBean;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public RespBean error(CustomerException e){
        return RespBean.error(e.getCode(),e.getMessage());
    }

    /**
     * 数据库异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = SQLException.class)
    public RespBean sqlException(SQLException e){
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("数据存在关联数据，操作异常");
        }
        return RespBean.error("数据库异常，操作失败");
    }




    /**
     * 捕捉 BindException
     * entity 实体的注解抛出的异常
     * @param e
     * @return
     */
    @ResponseBody       //全局异常处理 返回json
    @ExceptionHandler(value = BindException.class)
    public RespBean entityException(BindException e) {
        String msg=e.getBindingResult().getFieldError().getDefaultMessage();
        return  RespBean.error(msg);
    }

    /**
     * 捕捉 BindException
     * entity 实体的注解抛出的异常
     * @param e
     * @return
     */
//    @ResponseBody       //全局异常处理 返回json
//    @ExceptionHandler(value = ParamsException.class)
//    public RespBean paramsException(ParamsException e) {
//        String msg=e.getMsg();
//        long code=e.getCode();
//        return  RespBean.error(code,msg);
//    }

    /**
     * 捕捉 BindException
     * entity 实体的注解抛出的异常
     * @param e
     * @return
     */
//    @ResponseBody       //全局异常处理 返回json
//    @ExceptionHandler(value = IOException.class)
//    public RespBean iOException(IOException e) {
//        return  RespBean.error(700,"文件导出异常");
//    }


}
