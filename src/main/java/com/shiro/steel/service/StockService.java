package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.exception.MyException;

public interface StockService extends IService<Stock>{
	@Transactional
	Integer updateByPrimaryKey(Stock stock);
	
	@Transactional
	Integer batchUpdateBykey(String ids, String productids, String nums);
//	@Transactional
//	Boolean lock(String ids, String nums, String productids);
	@Transactional
	Boolean lock(String ids, String nums, String customerId, String customerName, String productids) throws MyException;

}
