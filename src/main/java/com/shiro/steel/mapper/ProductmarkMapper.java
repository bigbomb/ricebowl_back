package com.shiro.steel.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.Productmark;

public interface ProductmarkMapper extends BaseMapper<Productmark>{
    int deleteByPrimaryKey(Integer id);

    Productmark selectByPrimaryKey(Integer id);

    List<Productmark> selectAll();

    int updateByPrimaryKey(Productmark record);
}