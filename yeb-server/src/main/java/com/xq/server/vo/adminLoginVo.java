package com.xq.server.vo;/*
@author  xq  AKIAIOSFODNN7EXAMPLE
@date 2021/4/10 - 14:23 wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
手握梦想,心向旅行的第10天
三尺代码 一世天涯
*/

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AdminLogin对象", description = "")
public class adminLoginVo {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "验证码", required = true)
    private String Code;
}
