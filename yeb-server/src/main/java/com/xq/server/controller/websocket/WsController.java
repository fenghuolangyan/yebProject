package com.xq.server.controller.websocket;/*
@author  xq 
@date 2021/4/26 - 10:44 
手握梦想,心向旅行的第26天
三尺代码 一世天涯
*/

import com.xq.server.entity.Admin;
import com.xq.server.vo.ChatMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WsController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsgVo chatMsgVo){
        Admin admin = (Admin) authentication.getPrincipal();
        chatMsgVo.setFrom(admin.getUsername());
        chatMsgVo.setFromNickName(admin.getName());
        chatMsgVo.setDate(LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(chatMsgVo.getTo(),"/queue/chat",chatMsgVo);
    }
}
