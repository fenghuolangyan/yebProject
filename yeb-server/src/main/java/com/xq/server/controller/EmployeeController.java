package com.xq.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xq.common.utils.RespBean;
import com.xq.common.utils.result;
import com.xq.server.entity.*;
import com.xq.server.service.*;
import com.xq.server.vo.pageVo;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
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
@RequestMapping("/employee/basic")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private NationService nationService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private JoblevelService joblevelService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PoliticsStatusService politicsStatusService;

    @ApiOperation(value = "分页获取所有员工")
    @GetMapping("/getLimitEmployee")
    public pageVo getLimitEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   Employee employee, LocalDate[] beginDateScope) {
        return employeeService.getAllEmployee(currentPage, size, employee, beginDateScope);
    }

    @ApiOperation(value = "通过ID获取员工信息")
    @GetMapping("/getEmployeeById")
    public RespBean getEmployeeById(Integer id) {
        return RespBean.success("",employeeService.getListEmployee(id));
    }

    @ApiOperation(value = "导出EXCEL")
    @GetMapping(value = "/exportEmployee", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<Employee> list = employeeService.getListEmployee(null);
        ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, list);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder
                    .encode("员工表.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "导入EXCEL")
    @PostMapping("/importEmployee")
    public RespBean importEmployee(MultipartFile file) {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        List<Nation> nationList = nationService.list(null);
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list(null);
        List<Position> positionList = positionService.list(null);
        List<Department> departmentList = departmentService.list(null);
        List<Joblevel> joblevelList = joblevelService.list(null);
        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
//            for (Employee employee1 : list) {
//                Employee one = employeeService.getOne(new QueryWrapper<Employee>().eq("WorkID", employee.getWorkID()));
//                if(null != one){
//                    return RespBean.error("工号已存在");
//                }
//            }
            list.forEach(employee -> {
                employee.setNationId(nationList.get(nationList.indexOf(
                        new Nation(employee.getNation().getName()))).getId());
                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(
                        new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                employee.setPosId(positionList.get(positionList.indexOf(
                        new Position(employee.getPosition().getName()))).getId());
                employee.setDepartmentId(departmentList.get(departmentList.indexOf(
                        new Department(employee.getDepartment().getName()))).getId());
                employee.setJobLevelId(joblevelList.get(joblevelList.indexOf(
                        new Joblevel(employee.getJoblevel().getName()))).getId());
            });
            if (employeeService.saveBatch(list)) {
                return RespBean.success("导入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.success("导入失败");
    }

    @ApiOperation("员工添加")
    @PostMapping("/addEmployee")
    public RespBean addEmployee(@RequestBody Employee employee) {
        //根据身份证查询员工
        Employee dbEmployee = employeeService.selectByIdCard(employee.getIdCard());
        if (null != dbEmployee) {
            return RespBean.error("身份证号已存在");
        }
        return employeeService.addEmployee(employee);
    }

    @ApiOperation("员工删除")
    @DeleteMapping("/deleteEmployee/{id}")
    public RespBean deleteEmployee(@PathVariable("id") Integer id) {
        boolean delte = employeeService.removeById(id);
        return result.respBean(delte);
    }

    @ApiOperation("员工修改")
    @PutMapping("/updateEmployee")
    public RespBean updateEmployee(@RequestBody Employee employee) {
        boolean update = employeeService.updateById(employee);
        return result.respBean(update);
    }

    @ApiOperation("所有民族")
    @GetMapping("/getNations")
    public List<Nation> getNations() {
        List<Nation> list = nationService.list(null);
        return list;
    }

    @ApiOperation("所有部门")
    @GetMapping("/getDeps")
    public List<Department> getDeps() {
        List<Department> list = departmentService.getAllDepartment();
        return list;
    }

    @GetMapping("/getPositions")
    @ApiOperation("所有职位")
    public List<Position> getPositions() {
        return positionService.list(null);
    }

    @GetMapping("/getJoblevels")
    @ApiOperation("工作等级")
    public List<Joblevel> getJoblevels() {
        return joblevelService.list(null);
    }

    @GetMapping("/getPoliticsstatus")
    @ApiOperation("所有政治面貌")
    public List<PoliticsStatus> getPoliticsstatus() {
        return politicsStatusService.list(null);
    }

    @GetMapping("/getMaxWorkID")
    @ApiOperation("最大工号")
    public RespBean getMaxWorkID() {
        return employeeService.maxWorkId();
    }
}

