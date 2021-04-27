package com.xq.server.service;

import com.xq.common.utils.RespBean;
import com.xq.server.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.server.entity.Menu;
import com.xq.server.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xq
 * @since 2021-04-10
 */
public interface AdminService extends IService<Admin> {

    RespBean login(String username, String password, String code, HttpServletRequest request);

    Admin getAdminByUserName(String username);

    List<Role> getRoles(Integer adminId);

    List<Admin> getAllAdmin(String keyWord);

    RespBean updateRole(Integer adminId, Integer[] rids);

    RespBean updatrAdminPassWord(String oldPass, String pass, Integer adminId);
}
