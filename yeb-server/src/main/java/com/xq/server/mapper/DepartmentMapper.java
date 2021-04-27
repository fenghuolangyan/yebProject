package com.xq.server.mapper;

import com.xq.common.utils.RespBean;
import com.xq.server.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xq
 * @since 2021-04-14
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartment(@Param("parentId") Integer parentId);

    void addDep(Department dep);

    void deleteDep(Department dep);
}
