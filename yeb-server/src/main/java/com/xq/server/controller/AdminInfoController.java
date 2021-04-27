package com.xq.server.controller;/*
@author  xq 
@date 2021/4/27 - 11:42 
手握梦想,心向旅行的第27天
三尺代码 一世天涯
*/

import com.xq.common.utils.RespBean;
import com.xq.server.entity.Admin;
import com.xq.server.service.AdminService;
import com.xq.server.utils.MinioUtil;
import com.xq.server.vo.ChangeHeadImg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/info")
public class AdminInfoController {
    @Autowired
    private AdminService adminService;
    @Resource
    private MinioUtil minioUtil;

    @PostMapping("/userface")
    public ChangeHeadImg upload(ChangeHeadImg img, Principal principal){
//        log.info("上传文件"+img.getFile().getOriginalFilename());
        minioUtil.putObject("lwf", img.getFile(), principal.getName()+img.getFile().getOriginalFilename());
        //文件路径
        String url=minioUtil.getObjectUrl("lwf", principal.getName() + img.getFile().getOriginalFilename());
//        log.info(url);
        Admin admin=new Admin();
        admin.setEnabled(img.getEnabled());
        admin.setId(img.getId());
        admin.setUserFace(url);
        String fa=adminService.getAdminByUserName(principal.getName()).getUserFace();
        adminService.updateById(admin);
        minioUtil.removeObject("lwf", fa.substring(fa.lastIndexOf('/')));
        return img;
    }

    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/updateAdmin")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication)
    {
        if(adminService.updateById(admin)){
            SecurityContextHolder.getContext().
                    setAuthentication(new UsernamePasswordAuthenticationToken(
                            admin,null,authentication.getAuthorities()
                    ));
            return  RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "更新当前用户密码")
    @PutMapping("/updatePass")
    public RespBean updatePass(@RequestBody Map<String,Object> info)
    {
        String oldPass = (String)info.get("oldPass");
        String pass = (String)info.get("pass");
        Integer adminId = (Integer)info.get("adminId");
        return adminService.updatrAdminPassWord(oldPass,pass,adminId);
    }

}
