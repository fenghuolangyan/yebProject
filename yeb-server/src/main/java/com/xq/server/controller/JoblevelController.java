package com.xq.server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xq.common.utils.R;
import com.xq.common.utils.RespBean;
import com.xq.server.entity.Employee;
import com.xq.server.entity.Joblevel;
import com.xq.server.service.JoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xq
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/system/cfg/joblevel")
public class JoblevelController {
    @Autowired
    private JoblevelService joblevelService;

    @ApiOperation(value = "获取全部职称")
    @GetMapping("/getAlljoblevels/{currentPage}/{size}")
    public RespBean getAlljoblevels(@PathVariable Integer currentPage,
                                          @PathVariable Integer size) {
        Page<Joblevel> pageParam = new Page<>(currentPage,size);
        joblevelService.pageQuery(pageParam);
        long total = pageParam.getTotal();
        List<Joblevel> records = pageParam.getRecords();
        Map data = new HashMap<>();
        data.put("total", total);
        data.put("records", records);
        return RespBean.success("",data);
    }

    @ApiOperation(value = "修改职称")
    @PutMapping("/updatejoblevel")
    public RespBean updatejoblevel(@RequestBody Joblevel joblevel) {
        if (joblevel.getId() == null) {
            return RespBean.error("id空");
        }
        boolean update = joblevelService.updateById(joblevel);
        if(update){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除职称")
    @DeleteMapping("/deletejoblevel/{id}")
    public RespBean deletejoblevel(@PathVariable("id") Integer id) {
        boolean update = joblevelService.removeById(id);
        if(update){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/addjoblevel")
    public RespBean addjoblevel(@RequestBody Joblevel joblevel) {
        //根据职称名称查询数据库
        Joblevel dbjoblevel = joblevelService.selectByName(joblevel.getName());
        //判断是否存在同名职称
        if(dbjoblevel != null){
            return RespBean.error("职称已存在");
        }
        if (joblevel.getId() != null) {
            return RespBean.error("id不需要");
        }
        boolean update = joblevelService.save(joblevel);
        if(update){
            return RespBean.success("添加职称成功");
        }
        return RespBean.error("添加职称失败");
    }

    /**
     *      批量删除
     *          1.待删除id不能为空
     *          2.遍历id查询数据库，必须在存在内容
     *          3.执行操作
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除职称")
    @DeleteMapping("/batchDeletejoblevels")
    public RespBean batchDeletejoblevels(Integer[] ids) {
        //待删除id数组不能为空
        //执行操作
        boolean update = joblevelService.removeByIds(Arrays.asList(ids));
        if(update){
            return RespBean.success("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }

}

