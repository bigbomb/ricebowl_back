package com.shiro.steel.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.Supplyer;

public interface SupplyerMapper extends BaseMapper<Supplyer>{
    int deleteByPrimaryKey(Integer id);

    Supplyer selectByPrimaryKey(Integer id);

    List<Supplyer> selectAll();

    int updateByPrimaryKey(Supplyer record);
}