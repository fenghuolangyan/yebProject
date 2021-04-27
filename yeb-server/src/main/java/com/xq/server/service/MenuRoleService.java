package com.xq.server.service;

import com.xq.common.utils.RespBean;
import com.xq.server.entity.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xq
 * @since 2021-04-12
 */
public interface MenuRoleService extends IService<MenuRole> {

    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
