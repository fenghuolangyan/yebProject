package com.xq.server.task;/*
@author  xq 
@date 2021/4/23 - 14:51 
手握梦想,心向旅行的第23天
三尺代码 一世天涯
*/

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xq.common.Constants.MailConstants;
import com.xq.server.entity.Employee;
import com.xq.server.entity.MailLog;
import com.xq.server.service.MailLogService;
import com.xq.server.service.impl.EmployeeServiceImpl;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
//@EnableScheduling
public class MailTask {
    @Autowired
    private MailLogService mailLogService;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask() {
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>()
                .eq("status", MailConstants.DELIVERING)
                 //每隔一分钟重发一次
                .lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            String msgId = mailLog.getMsgId();
            if (mailLog.getCount() >= MailConstants.MAX_TRY_COUNT) {
                mailLogService.update(new MailLog(), new UpdateWrapper<MailLog>()
                        .eq("msgId", msgId)
                        .set("status", MailConstants.FAILURE)
                        .set("updateTime", LocalDateTime.now()));
            }
            mailLogService.update(new MailLog(), new UpdateWrapper<MailLog>()
                    .eq("msgId", msgId)
                    .set("count", mailLog.getCount() + 1)
                    .set("updateTime", LocalDateTime.now())
                    .set("tryTime", LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)));
            Employee employee = employeeService.getListEmployee(mailLog.getEid()).get(0);
            //发送信息 发送邮件添加唯一标识
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME
                    , employee, new CorrelationData(msgId));
        });
    }
}
