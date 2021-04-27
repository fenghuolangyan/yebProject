package com.xq.server.service.impl;

import com.xq.common.utils.RespBean;
import com.xq.common.utils.result;
import com.xq.server.entity.Department;
import com.xq.server.mapper.DepartmentMapper;
import com.xq.server.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xq
 * @since 2021-04-14
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment(-1);
    }

    @Override
    public RespBean addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
        if (1 == dep.getResult()) {
//            Map map = new HashMap<>();
//            List<Department> departments = departmentMapper.getAllDepartment(-1);
//            map.put("deps",departments);
//            map.put("dep",dep);
            return RespBean.success("添加成功", dep);
        }
        return RespBean.error("添加失败");
    }

    @Override
    public RespBean deleteDep(Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentMapper.deleteDep(dep);
        if (-2 == dep.getResult()) {
            return RespBean.error("该部门下还有子部门,删除失败!");
        }
        if (-1 == dep.getResult()) {
            return RespBean.error("该部门下还有员工,删除失败!");
        }
        return result.respBean(1 == dep.getResult());
    }
}
