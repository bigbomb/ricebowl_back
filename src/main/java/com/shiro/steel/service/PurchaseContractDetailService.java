package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.PurchaseContractDetail;

public interface PurchaseContractDetailService extends IService<PurchaseContractDetail>{
	@Transactional
	void deleteBatchNos(List<String> contractnos);
	@Transactional
	void updateByDetail(PurchaseContractDetail purchaseContractDetail);


}
