package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.steel.entity.WarehouseInfo;
import com.shiro.steel.pojo.dto.ParamsDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WarehouseInfoMapper extends BaseMapper<WarehouseInfo>{
    int deleteByPrimaryKey(Integer id);

    WarehouseInfo selectByPrimaryKey(Integer id);

    List<WarehouseInfo> selectAll();

    int updateByPrimaryKey(WarehouseInfo record);

	List<WarehouseInfo> findByPage(Pagination page, @Param("dto")ParamsDto dto, String memberId);
}