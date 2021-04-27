package com.xq.server.controller.websocket;/*
@author  xq 
@date 2021/4/27 - 11:13 
手握梦想,心向旅行的第27天
三尺代码 一世天涯
*/

import com.xq.server.service.AdminService;
import com.xq.server.vo.MessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api("发消息，有效期一天")
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatWithRedisController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private AdminService adminService;
    @ApiOperation("发送信息,有效期默认一天")
    @PostMapping("/send")
    public MessageVo sendMessage(@RequestBody MessageVo message, Principal principal){
        message.setNotSelf(principal.getName());
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        message.setTime(format.format(new Date()));
        //hash存  发起人 接收人  消息key
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        //有效一天，作为消息key
        LocalDate date=LocalDate.now();
        String msgKey=message.getNotSelf()+message.getTo()+ date.toString().trim();
        stringObjectObjectHashOperations.put(message.getNotSelf(), message.getTo(),msgKey);
        //存当天消息  消息key  消息列表     一天过期
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPush(msgKey, message);
        redisTemplate.expire(msgKey, 1, TimeUnit.DAYS);
        return message;
    }
    @ApiOperation("取消息")
    @GetMapping("/get")
    public List<MessageVo> getMessage(String username, Principal principal){
        //今天的消息key
        LocalDate date=LocalDate.now();
        String msgKey=username+principal.getName()+ date.toString().trim();
        log.info("用户"+principal.getName()+",使用"+msgKey+"取消息");
        //取当天消息
        ListOperations<String, Object> list = redisTemplate.opsForList();
        List<MessageVo> msg=new ArrayList<>();
        list.range(msgKey,0,-1).forEach(o->{
            MessageVo message = (MessageVo)o ;
            message.setSelf(true);
            message.setUserFace(adminService.getAdminByUserName(message.getNotSelf()).getUserFace());
            msg.add(message);
        });
        String msgKey1=principal.getName()+username+ date.toString().trim();
        //取当天消息
        list.range(msgKey1,0,-1).forEach(o->{
            MessageVo message = (MessageVo)o ;
            message.setSelf(true);
            message.setUserFace(adminService.getAdminByUserName(message.getNotSelf()).getUserFace());
            msg.add(message);
        });
        Collections.sort(msg);
        return msg;
    }
}
