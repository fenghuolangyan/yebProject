package com.xq.server.mapper;

import com.xq.server.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xq
 * @since 2021-04-12
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenusByAdminId(@Param("id") Integer id);

    List<Menu> getMenusWithRole();

    List<Menu> getAllMenus();
}
