package com.shiro.steel.service.ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.mapper.StockMapper;
import com.shiro.steel.service.StockService;

@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService{

	@Autowired
	private StockMapper stockMapper;
	@Override
	public Integer updateByPrimaryKey(Stock stock) {
		// TODO Auto-generated method stub
		return stockMapper.updateByPrimaryKey(stock);
	}



}

