package com.xq.server.mapper;

import com.xq.common.utils.RespBean;
import com.xq.server.entity.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xq
 * @since 2021-04-12
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer addRole(@Param("adminId") Integer adminId,@Param("rids") Integer[] rids);
}
