package com.xq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xq.common.Constants.MailConstants;
import com.xq.common.utils.RespBean;
import com.xq.common.utils.result;
import com.xq.server.entity.Employee;
import com.xq.server.entity.MailLog;
import com.xq.server.mapper.EmployeeMapper;
import com.xq.server.mapper.MailLogMapper;
import com.xq.server.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.server.vo.pageVo;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xq
 * @since 2021-04-14
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MailLogMapper mailLogMapper;

    @Override
    public pageVo getAllEmployee(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> iPage = employeeMapper.getAllEmployee(page, employee, beginDateScope);
        pageVo pageVo = new pageVo(iPage.getTotal(), iPage.getRecords());
        return pageVo;
    }

    @Override
    public RespBean maxWorkId() {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        List<Map<String, Object>> maps = employeeMapper.selectMaps(wrapper.select("max(workID)"));
        String maxID = String.format("%08d", Integer.parseInt(maps.get(0).get("max(workID)").toString()) + 1);
        return RespBean.success(null, maxID);
    }

    @Override
    public Employee selectByIdCard(String idCard) {
        return this.baseMapper.selectOne(new QueryWrapper<Employee>().eq("idCard", idCard));
    }

    @Override
    public RespBean addEmployee(Employee employee) {
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days / 365.00)));
        if (1 == employeeMapper.insert(employee)) {
            Employee emp = employeeMapper.selectListEmployeeMapper(employee.getId()).get(0);

            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setCount(0);
            mailLog.setEid(emp.getId());
            mailLog.setStatus(0);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLogMapper.insert(mailLog);
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME
                    ,emp,new CorrelationData(msgId));
            return RespBean.success("添加成功");
        }
        return RespBean.success("添加失败");
    }

    @Override
    public List<Employee> getListEmployee(Integer id) {
        return employeeMapper.selectListEmployeeMapper(id);
    }

    @Override
    public pageVo getEmployeeWithSalary(Integer currentPage, Integer size) {
        Page<Employee> page = new Page(currentPage, size);
        IPage<Employee>employeeIPage = employeeMapper.getEmployeeWithSalary(page);
        pageVo pageVo = new pageVo(employeeIPage.getTotal(),employeeIPage.getRecords());
        return pageVo;
    }

}
