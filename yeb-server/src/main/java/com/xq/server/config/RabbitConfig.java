package com.xq.server.config;/*
@author  xq 
@date 2021/4/23 - 11:04 
手握梦想,心向旅行的第23天
三尺代码 一世天涯
*/

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xq.common.Constants.MailConstants;
import com.xq.server.entity.MailLog;
import com.xq.server.service.MailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    private MailLogService mailLogService;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData data, boolean ack, String cause) {
                String msgId = data.getId();
                if(ack){
                    LOGGER.info("{}======>消息发送成功",msgId);
                    mailLogService.update(new MailLog(),new UpdateWrapper<MailLog>()
                            .set("status",MailConstants.SUCCESS)
                            .eq("msgId",msgId));
                }else{
                    LOGGER.error("{}======>消息发送失败",msgId);
                }
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 消息失败回调
             * @param message  消息主题
             * @param replyCode  响应码
             * @param replyText  响应描述
             * @param exchange  交换机
             * @param routingKey  路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                LOGGER.error("{}======>消息发送queue时失败",message.getBody());
            }
        });

        return rabbitTemplate;
    }


    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME,true,false);
    }

    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME,true,false,false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}
