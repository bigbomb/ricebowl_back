package com.shiro.steel.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.Product;

public interface ProductMapper extends BaseMapper<Product>{
    int deleteByPrimaryKey(Integer id);

    Product selectByPrimaryKey(Integer id);

    List<Product> selectAll();

    int updateByPrimaryKey(Product record);
}