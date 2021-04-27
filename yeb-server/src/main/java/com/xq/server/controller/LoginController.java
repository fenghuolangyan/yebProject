package com.xq.server.controller;/*
@author  xq 
@date 2021/4/10 - 14:27 
手握梦想,心向旅行的第10天
三尺代码 一世天涯
*/

import com.xq.common.utils.RespBean;
import com.xq.server.entity.Admin;
import com.xq.server.service.AdminService;
import com.xq.server.vo.adminLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录控制器
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Autowired
    private AdminService adminService;


    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    //request获取session信息
    public RespBean login(@RequestBody adminLoginVo adminLoginParam, HttpServletRequest request) {
        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(),
                adminLoginParam.getCode(), request);
    }


    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/admin/info")
//    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//    Principal能获取到用户数据是在login时设置了用户信息
    public Admin getAdminInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }


    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功！");
    }
}
