package com.shiro.steel.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.Productfactory;

public interface ProductfactoryMapper extends BaseMapper<Productfactory>{
    int deleteByPrimaryKey(Integer id);

    Productfactory selectByPrimaryKey(Integer id);

    List<Productfactory> selectAll();

    int updateByPrimaryKey(Productfactory record);
}