package com.xq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xq.server.entity.Position;
import com.xq.server.mapper.PositionMapper;
import com.xq.server.service.PositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xq
 * @since 2021-04-13
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Override
    public Position selectByName(String name) {
        if (name != null) {
            name = name.trim();
            return baseMapper.selectOne(new QueryWrapper<Position>().eq("name", name));
        }
        return null;
    }
}
