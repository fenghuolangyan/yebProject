package com.xq.server.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xq.server.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xq
 * @since 2021-04-14
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工分页
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    IPage<Employee> getAllEmployee(Page<Employee> page, @Param("employee") Employee employee,@Param("beginDateScope") LocalDate[] beginDateScope);

    List<Employee> selectListEmployeeMapper(@Param("id") Integer id);

    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
