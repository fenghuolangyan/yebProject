package com.xq.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xq.server.entity.Joblevel;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xq
 * @since 2021-04-13
 */
public interface JoblevelService extends IService<Joblevel> {

    Joblevel selectByName(String name);

    void pageQuery(Page<Joblevel> page);
}
