package com.xq.common.Constants;/*
@author  xq 
@date 2021/4/23 - 14:31 
手握梦想,心向旅行的第23天
三尺代码 一世天涯
*/

public class MailConstants {
    //消息投递中
    public static final Integer DELIVERING = 0;

    //消息投成功
    public static final Integer SUCCESS = 1;

    //消息投递失败
    public static final Integer FAILURE = 2;

    //最大重试次数
    public static final Integer MAX_TRY_COUNT = 2;

    //消息超时时间
    public static final Integer MSG_TIMEOUT = 1;

    //交换机
    public static final String MAIL_EXCHANGE_NAME = "mail-exchange";

    //队列
    public static final String MAIL_QUEUE_NAME = "mail.queue";

    //路由键
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";
}
