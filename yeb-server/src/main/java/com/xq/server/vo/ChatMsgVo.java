package com.xq.server.vo;/*
@author  xq 
@date 2021/4/26 - 10:40 
手握梦想,心向旅行的第26天
三尺代码 一世天涯
*/

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.concurrent.PriorityBlockingQueue;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsgVo {
    private String from;
    private String to;
    private String content;
    private LocalDateTime date;
    private String fromNickName;
}
