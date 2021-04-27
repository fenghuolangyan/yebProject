package com.xq.server.service;

import com.xq.common.utils.RespBean;
import com.xq.server.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.server.vo.pageVo;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xq
 * @since 2021-04-14
 */
public interface EmployeeService extends IService<Employee> {

    pageVo getAllEmployee(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    RespBean maxWorkId();

    Employee selectByIdCard(String idCard);


    RespBean addEmployee(Employee employee);

    List<Employee> getListEmployee(Integer id);

    pageVo getEmployeeWithSalary(Integer currentPage, Integer size);
}
