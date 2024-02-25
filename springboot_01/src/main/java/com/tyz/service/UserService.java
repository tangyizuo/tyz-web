package com.tyz.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyz.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author springboot_01
 * @since 2024-02-21
 */
public interface UserService extends IService<User> {

    IPage pageC(Page<User> page);

    IPage pageCC(IPage<User> page, Wrapper wrapper);
}
