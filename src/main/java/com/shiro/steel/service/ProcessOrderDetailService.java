package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.ProcessOrderDetail;

public interface ProcessOrderDetailService extends IService<ProcessOrderDetail>{
	@Transactional
	void deleteBatchProcessNos(List<String> asList);

}
