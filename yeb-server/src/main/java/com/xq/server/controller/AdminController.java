package com.xq.server.controller;


import com.xq.common.utils.RespBean;
import com.xq.common.utils.result;
import com.xq.server.entity.Admin;
import com.xq.server.entity.Role;
import com.xq.server.service.AdminService;
import com.xq.server.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xq
 * @since 2021-04-10
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {
    @Autowired
    private AdminService AdminService;
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取全部人员")
    @GetMapping("/getAllAdmin")
    public List<Admin> getAllAdmin(String keyWord) {
        return AdminService.getAllAdmin(keyWord);
    }

    @ApiOperation(value = "更新人员")
    @PutMapping("/updateAdmin")
    public RespBean updateAdmin(@RequestBody Admin admin){
        return result.respBean(AdminService.updateById(admin));
    }

    @ApiOperation(value = "删除人员")
    @DeleteMapping("/deleteAdmin/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id){
        return result.respBean(AdminService.removeById(id));
    }

    @ApiOperation(value = "获取全部角色")
    @GetMapping("/getAllRole")
    public List<Role> getAllRole() {
        return roleService.list(null);
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/updateRole")
    public RespBean updateRole(Integer adminId,Integer[] rids){
        return AdminService.updateRole(adminId,rids);
    }

}

