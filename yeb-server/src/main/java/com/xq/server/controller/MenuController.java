package com.xq.server.controller;


import com.xq.server.entity.Menu;
import com.xq.server.service.AdminService;
import com.xq.server.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xq
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @ApiOperation(value = "通过用户id获取菜单列表")
    @GetMapping("/getMenusByAdminId")
    public List<Menu> getMenusByAdminId() {
        return menuService.getMenusByAdminId();
    }

}

