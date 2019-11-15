package com.shiro.steel.service.ipml;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.PurchaseContractDetail;
import com.shiro.steel.mapper.PurchaseContractDetailMapper;
import com.shiro.steel.service.PurchaseContractDetailService;
@Service
public class PurchaseContractDetailServiceImpl extends ServiceImpl<PurchaseContractDetailMapper, PurchaseContractDetail> implements PurchaseContractDetailService{

	@Override
	public void deleteBatchNos(List<String> contractnos) {
		// TODO Auto-generated method stub
		super.baseMapper.deleteBatchNos(contractnos);
	}

	@Override
	public void updateByDetail(PurchaseContractDetail purchaseContractDetail) {
		// TODO Auto-generated method stub
		super.baseMapper.updateByDetail(purchaseContractDetail);
	}



}
