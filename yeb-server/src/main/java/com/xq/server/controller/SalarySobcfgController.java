package com.xq.server.controller;/*
@author  xq 
@date 2021/4/26 - 9:20 
手握梦想,心向旅行的第26天
三尺代码 一世天涯
*/

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xq.common.utils.RespBean;
import com.xq.server.entity.Employee;
import com.xq.server.entity.Salary;
import com.xq.server.service.EmployeeService;
import com.xq.server.service.SalaryService;
import com.xq.server.vo.pageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(value="员工账套管理")
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobcfgController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SalaryService salaryService;

    @ApiOperation(value = "获取所有员工账套")
    @GetMapping("/getEmployeeWithSalary")
    public pageVo getEmployeeWithSalary(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size){
        pageVo pageVo = employeeService.getEmployeeWithSalary(currentPage,size);
        return pageVo;
    }

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/getSalaries")
    public List<Salary> getSalaries(){
        return salaryService.list(null);
    }

    @ApiOperation(value = "更新员工工资账套")
    @PutMapping("/updateEmployeeSalary")
    public RespBean updateEmployeeSalary(Integer eid, Integer sid){
        if(employeeService.update(new Employee(),new UpdateWrapper<Employee>()
        .set("salaryId",sid).eq("id",eid))){
            return RespBean.success("更新成功");
        }
        return RespBean.success("更新失败");
    }
}
