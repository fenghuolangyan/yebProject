package com.xq.server.controller;


import com.xq.common.utils.RespBean;
import com.xq.server.entity.Position;
import com.xq.server.service.PositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xq
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/system/cfg/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @ApiOperation(value = "获取全部职位")
    @GetMapping("/getAllPositions")
    public List<Position> getAllPositions() {
        return positionService.list(null);
    }

    @ApiOperation(value = "修改职位")
    @PutMapping("/updatePosition")
    public RespBean updatePosition(@RequestBody Position position) {
        if (position.getId() == null) {
            return RespBean.error("id空");
        }
        boolean update = positionService.updateById(position);
        if(update){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除职位")
    @DeleteMapping("/deletePosition/{id}")
    public RespBean deletePosition(@PathVariable("id") Integer id) {
        boolean update = positionService.removeById(id);
        if(update){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "添加职位")
    @PostMapping("/addPosition")
    public RespBean addPosition(@RequestBody Position position) {
        //根据职位名称查询数据库
        Position dbPosition = positionService.selectByName(position.getName());
        //判断是否存在同名职位
        if(dbPosition != null){
            return RespBean.error("部门名称已存在");
        }
        if (position.getId() != null) {
            return RespBean.error("id不需要");
        }
        boolean update = positionService.save(position);
        if(update){
            return RespBean.success("添加职位成功");
        }
         return RespBean.error("添加职位失败");
    }

    /**
     *      批量删除
     *          1.待删除id不能为空
     *          2.遍历id查询数据库，必须在存在内容
     *          3.执行操作
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除职位")
    @DeleteMapping("/batchDeletePositions")
    public RespBean batchDeletePositions(Integer[] ids) {
        //待删除id数组不能为空
//        if (ids == null || ids.length == 0) {
//            return RespBean.error("id没有传过来");
//        }
        //根据id查询数据库是否存在
//        for (Integer id:ids){
//            Position dbPosition = positionService.getById(id);
//            AssertUtil.isTrue(dbPosition==null,"待删除数据不存在");
//        }
        //执行操作
        boolean update = positionService.removeByIds(Arrays.asList(ids));
        if(update){
            return RespBean.success("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }



}

