package com.xq.server.controller;


import com.xq.common.utils.RespBean;
import com.xq.server.entity.Salary;
import com.xq.server.service.SalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xq
 * @since 2021-04-26
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    private SalaryService SalaryService;

    @ApiOperation(value = "获取全部工资账套")
    @GetMapping("/getAllSalarys")
    public List<Salary> getAllSalarys() {
        return SalaryService.list(null);
    }

    @ApiOperation(value = "修改工资账套")
    @PutMapping("/updateSalary")
    public RespBean updateSalary(@RequestBody Salary Salary) {
        if (Salary.getId() == null) {
            return RespBean.error("id空");
        }
        boolean update = SalaryService.updateById(Salary);
        if(update){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除工资账套")
    @DeleteMapping("/deleteSalary/{id}")
    public RespBean deleteSalary(@PathVariable("id") Integer id) {
        boolean update = SalaryService.removeById(id);
        if(update){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "添加工资账套")
    @PostMapping("/addSalary")
    public RespBean addSalary(@RequestBody Salary Salary) {
        if (Salary.getId() != null) {
            return RespBean.error("id不需要");
        }
        boolean update = SalaryService.save(Salary);
        if(update){
            return RespBean.success("添加工资账套成功");
        }
        return RespBean.error("添加工资账套失败");
    }

    /**
     *      批量删除
     *          1.待删除id不能为空
     *          2.遍历id查询数据库，必须在存在内容
     *          3.执行操作
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除工资账套")
    @DeleteMapping("/batchDeleteSalarys")
    public RespBean batchDeleteSalarys(Integer[] ids) {
        //执行操作
        boolean update = SalaryService.removeByIds(Arrays.asList(ids));
        if(update){
            return RespBean.success("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }
}

