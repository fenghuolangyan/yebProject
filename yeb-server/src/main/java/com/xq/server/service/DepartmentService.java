package com.xq.server.service;

import com.xq.common.utils.RespBean;
import com.xq.server.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xq
 * @since 2021-04-14
 */
public interface DepartmentService extends IService<Department> {

    List<Department> getAllDepartment();

    RespBean addDep(Department dep);

    RespBean deleteDep(Integer id);
}
