package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.steel.entity.User;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.UserDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
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
public interface UserMapper extends BaseMapper<User> {

    /**
     * @desc: 查询用户
     *
     * @param page 分页
     * @param dto 参数dto
     * @author: jwy
     * @param memberId 
     * @date: 2017/12/19
     */
    List<UserDto> findUserByPage(Pagination page,@Param("dto") ParamsDto dto, @Param("memberId")String memberId);

    /**
     * @desc: 登陆验证
     *
     * @author: jwy
     * @date: 2017/12/27
     */
    List<UserInfoDto> checkUser(@Param("name") String name, @Param("pass") String pass);
}