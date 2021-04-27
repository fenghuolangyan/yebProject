package com.xq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xq.common.utils.RespBean;
import com.xq.common.utils.result;
import com.xq.server.entity.Admin;
import com.xq.server.entity.AdminRole;
import com.xq.server.entity.Menu;
import com.xq.server.entity.Role;
import com.xq.server.mapper.AdminMapper;
import com.xq.server.mapper.AdminRoleMapper;
import com.xq.server.mapper.RoleMapper;
import com.xq.server.secrity.utils.JwtTokenUtil;
import com.xq.server.service.AdminRoleService;
import com.xq.server.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.server.utils.AdminUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xq
 * @since 2021-04-10
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    //    @Resource
//    private SysMsgMapper sysMsgMapper;
//    SpringSecurity自带的service
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //登录验证  CaptchaController里request.getSession().setAttribute("captcha", text);
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (!captcha.equals(code) || StringUtils.isBlank(captcha)) {
            return RespBean.error("验证码输入错误,请重新填写！");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确!");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员!");
        }
//        更新security登录用户对象 密码要设置为空值防止暴露
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("serviceImpl的Token = " + token);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功", tokenMap);
    }

    @Override
    public Admin getAdminByUserName(String username) {
        return baseMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
    }

    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    @Override
    public List<Admin> getAllAdmin(String keyWord) {
        return adminMapper.getAllAdmin(AdminUtil.getCurrentAdmin().getId(),keyWord);
    }

    @Override
    @Transactional
    public RespBean updateRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer recodes = adminRoleMapper.addRole(adminId, rids);
        return result.respBean(recodes == rids.length);
    }

    @Override
    public RespBean updatrAdminPassWord(String oldPass, String pass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();
        if(encoder.matches(oldPass,admin.getPassword())){
            admin.setPassword(encoder.encode(pass));
            int result = adminMapper.updateById(admin);
            if(1 == result){
                return RespBean.success("更新成功");
            }
        }
        return RespBean.error("旧密码错误");
    }
}
