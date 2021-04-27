package com.xq.server.service.impl;

import com.xq.server.entity.Admin;
import com.xq.server.entity.Menu;
import com.xq.server.entity.Role;
import com.xq.server.mapper.MenuMapper;
import com.xq.server.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xq
 * @since 2021-04-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Menu> getMenusByAdminId() {
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        System.out.println("adminId = " + adminId);
        List<Menu> menus = null;
        menus = (List<Menu>)redisTemplate.opsForValue().get("admin_" + adminId);
        System.out.println("menus = " + menus);
        if(CollectionUtils.isEmpty(menus)) {
            menus = menuMapper.getMenusByAdminId(adminId);
            redisTemplate.opsForValue().set("admin_" + adminId,menus);
        }
        return menus;
    }

    @Override
    public List<Menu> getMenusWithRole() {
        List<Menu> menus = menuMapper.getMenusWithRole();
        return menus;
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
