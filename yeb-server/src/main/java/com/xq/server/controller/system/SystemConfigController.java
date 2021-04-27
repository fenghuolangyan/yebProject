//package com.xq.server.controller.system;/*
//@author  xq
//@date 2021/4/12 - 15:15
//手握梦想,心向旅行的第12天
//三尺代码 一世天涯
//*/
//
//import com.xq.server.entity.Menu;
//import com.xq.server.service.AdminService;
//import com.xq.server.service.MenuService;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@RestController
//@RequestMapping("/system")
//public class SystemConfigController {
//    @Autowired
//    private AdminService adminService;
//    @ApiOperation(value = "通过用户id获取菜单列表")
//    @GetMapping("/menu")
//    public List<Menu> getMenusByAdminId() {
//        return adminService.getMenusByAdminId();
//    }
//}
