package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.steel.entity.Role;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * @desc: 查询角色
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    List<Role> findRoleByPage(Pagination page,@Param("dto") ParamsDto dto);

    /**
     * 绑定角色下拉框
     *
     * @return
     */
    List<Role> findAllRoles();
}