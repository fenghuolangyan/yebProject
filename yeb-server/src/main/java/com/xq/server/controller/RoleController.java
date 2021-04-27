package com.xq.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xq.common.utils.RespBean;
import com.xq.common.utils.result;
import com.xq.server.entity.Menu;
import com.xq.server.entity.MenuRole;
import com.xq.server.entity.Role;
import com.xq.server.service.MenuRoleService;
import com.xq.server.service.MenuService;
import com.xq.server.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xq
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/system/basic/permission")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRoleService menuRoleService;
    @ApiOperation(value = "权限组数据接口")
    @GetMapping("/getAllRoles")
    public List<Role> getAllRoles() {
        return roleService.list(null);
    }

    //权限组
    @ApiOperation(value = "添加角色")
    @PostMapping("/addRole")
    public RespBean addRole(@RequestBody Role role) {
        if (role.getId() != null) {
            return RespBean.error("id不需要");
        }
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName().toUpperCase());
        }
        return result.respBean(roleService.save(role));
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/deleteRole/{id}")
    public RespBean deleteRole(@PathVariable("id") Integer id) {
        return result.respBean(roleService.removeById(id));
    }

    @ApiOperation(value = "获取全部菜单列表")
    @GetMapping("/getAllMenus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/getMidByRid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid) {
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",rid))
                .stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/updateMenuRole")
    public RespBean updateMenuRole(Integer rid,Integer[] mids) {
        return menuRoleService.updateMenuRole(rid,mids);
    }

}

