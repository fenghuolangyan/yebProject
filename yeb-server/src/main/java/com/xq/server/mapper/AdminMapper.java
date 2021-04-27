package com.xq.server.mapper;

import com.xq.server.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.server.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xq
 * @since 2021-04-10
 */
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> getAllAdmin(@Param("id") Integer id,@Param("keyWord") String keyWord);
}
