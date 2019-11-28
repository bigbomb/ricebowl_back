package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.Stock;

public interface StockService extends IService<Stock>{
	@Transactional
	Integer updateByPrimaryKey(Stock stock);
	
	@Transactional
	Integer batchUpdateBykey(String ids, String productids, String nums);

}
