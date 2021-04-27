package com.xq.mail.receiver;/*
@author  xq 
@date 2021/4/23 - 10:13 
手握梦想,心向旅行的第23天
三尺代码 一世天涯
*/

import com.rabbitmq.client.Channel;
import com.xq.common.Constants.MailConstants;
import com.xq.common.utils.RespBean;
import com.xq.mail.vo.empVo;
import com.xq.server.entity.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class mailReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(mailReceiver.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RedisTemplate redisTemplate;


    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) {
        Employee emp = (Employee) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        long tag = (long)headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = ((String) headers.get("spring_returned_message_correlation"));
        HashOperations hashOperations = redisTemplate.opsForHash();
        try {
            if(hashOperations.entries("mail_log").containsKey(msgId)){
                LOGGER.error("消息已经被消费==========>{}",msgId);
                //手动确认
                channel.basicAck(tag,false);
                return;
            }
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(emp.getEmail());
            helper.setSubject("入职欢迎");
            helper.setSentDate(new Date());
            //邮件内容
            Context context = new Context();
            context.setVariable("id", emp.getId());
            context.setVariable("name", emp.getName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String birthDay = sdf.format(emp.getBirthday());
            context.setVariable("birthday", birthDay);
            context.setVariable("position", emp.getPosition().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            javaMailSender.send(msg);
            LOGGER.info("邮件发送成功");
            hashOperations.put("mail_log",msgId,"ok");
            redisTemplate.expire("mail_log",10,TimeUnit.MINUTES);
            channel.basicAck(tag,false);
        } catch (Exception e) {
            try {
                channel.basicNack(tag,false,true);
            } catch (IOException ex) {
                LOGGER.error("邮件发送失败====>{}",e.getMessage());
            }
            LOGGER.error("邮件发送失败====>{}",e.getMessage());
        }
    }
}
