package com.shiro.steel.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.Productspec;

public interface ProductspecMapper extends BaseMapper<Productspec>{
    int deleteByPrimaryKey(Integer id);

    Productspec selectByPrimaryKey(Integer id);

    List<Productspec> selectAll();

    int updateByPrimaryKey(Productspec record);
}