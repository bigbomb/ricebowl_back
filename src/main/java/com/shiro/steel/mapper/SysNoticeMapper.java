package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.steel.entity.SysNotice;
import com.shiro.steel.pojo.dto.ParamsDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysNoticeMapper extends BaseMapper<SysNotice>{
    /**
     * @desc: 查询属性
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    List<SysNotice> findSysNoticeByPage(@Param("memberId") String memberId);

	List<SysNotice> findNoticeByPage(@Param("page")Page<SysNotice> page,@Param("dto")ParamsDto dto);
}