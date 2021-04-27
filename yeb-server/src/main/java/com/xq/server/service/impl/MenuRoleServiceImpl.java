package com.xq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xq.common.utils.RespBean;
import com.xq.server.entity.MenuRole;
import com.xq.server.mapper.MenuRoleMapper;
import com.xq.server.service.MenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xq
 * @since 2021-04-12
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        //如果mids为空则代表该角色分配菜单全删除
        if (null == mids || 0 == mids.length) {
            return RespBean.success("更新成功！");
        }
        Integer records = menuRoleMapper.insertRecord(rid, mids);
        if (records == mids.length) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败");
    }
}
