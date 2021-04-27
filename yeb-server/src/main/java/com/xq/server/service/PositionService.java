package com.xq.server.service;

import com.xq.server.entity.Position;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xq
 * @since 2021-04-13
 */
public interface PositionService extends IService<Position> {

    Position selectByName(String name);
}
