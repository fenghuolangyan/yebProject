package com.xq.server.service;

import com.xq.server.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.server.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xq
 * @since 2021-04-12
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getMenusByAdminId();

    List<Menu> getMenusWithRole();

    List<Menu> getAllMenus();
}
