package com.xq.server.controller.websocket;/*
@author  xq 
@date 2021/4/26 - 10:42 
手握梦想,心向旅行的第26天
三尺代码 一世天涯
*/

import com.xq.server.entity.Admin;
import com.xq.server.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "获取所有管理员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmins(String keywords){
        return adminService.getAllAdmin(keywords);
    }
}
