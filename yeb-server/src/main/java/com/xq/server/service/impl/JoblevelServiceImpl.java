package com.xq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xq.server.entity.Joblevel;
import com.xq.server.entity.Position;
import com.xq.server.mapper.JoblevelMapper;
import com.xq.server.service.JoblevelService;
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
public class JoblevelServiceImpl extends ServiceImpl<JoblevelMapper, Joblevel> implements JoblevelService {

    @Override
    public Joblevel selectByName(String name) {
        if (name != null) {
            name = name.trim();
            return baseMapper.selectOne(new QueryWrapper<Joblevel>().eq("name", name));
        }
        return null;
    }

    @Override
    public void pageQuery(Page<Joblevel> pageParam) {
        QueryWrapper<Joblevel> queryWrapper = new QueryWrapper<>();
        IPage<Joblevel> JoblevelIPage = this.baseMapper.selectPage(pageParam, queryWrapper);
        return;
    }
}
