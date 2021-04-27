package com.xq.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xq.common.utils.RespBean;
import com.xq.server.entity.Department;
import com.xq.server.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xq
 * @since 2021-04-14
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "获取全部部门")
    @GetMapping("/getAllDepartment")
    public List<Department> getAllDepartment() {
         return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "添加部门")
    @PostMapping("/addDep")
    public RespBean addDep(@RequestBody Department dep){
        if(!"".equals(dep.getName())){
            Department name = departmentService.getOne(new QueryWrapper<Department>().eq("name", dep.getName()));
            if(null != name){
                return RespBean.error("部门名称已存在");
            }
        }
        return departmentService.addDep(dep);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/deleteDep/{id}")
    public RespBean deleteDep(@PathVariable Integer id){
        return departmentService.deleteDep(id);
    }

}

