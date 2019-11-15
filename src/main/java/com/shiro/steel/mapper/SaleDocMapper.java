package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.SaleDoc;
import java.util.List;

public interface SaleDocMapper extends BaseMapper<SaleDoc>{
    int deleteByPrimaryKey(Integer id);

    SaleDoc selectByPrimaryKey(Integer id);

    List<SaleDoc> selectAll();

    int updateByPrimaryKey(SaleDoc record);
}