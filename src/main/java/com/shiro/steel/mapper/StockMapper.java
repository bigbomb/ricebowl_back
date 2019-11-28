package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.SaleContractTerm;
import com.shiro.steel.entity.Stock;
import java.util.List;

public interface StockMapper extends BaseMapper<Stock>{
    int deleteByPrimaryKey(Integer id);

    Stock selectByPrimaryKey(Integer id);

    List<Stock> selectAll();

    int updateByPrimaryKey(Stock record);

	int batchUpdateBykeys(List<Stock> stock);
	
    int batchAddBykeys(List<Stock> addstocklist);
}