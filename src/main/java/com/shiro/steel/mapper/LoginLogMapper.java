package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.steel.entity.LoginLog;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jwy
 * @since 2018-01-11
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 查询登录次数
     *
     * @param id
     * @return
     */
    Integer findMaxLoginTatalByUserId(@Param("id") String id);

    /**
     * 用户登录日志
     *
     * @param dto 参数dto
     * @return
     */
    List<LoginLog> findUserLoginLogByPage(Pagination page, @Param("dto") ParamsDto dto);

    /**
     * 查询用户登录总次数
     *
     * @author: jwy
     * @date: 2018/1/11
     */
    List<LoginLog> findUserLoginTotal();
}