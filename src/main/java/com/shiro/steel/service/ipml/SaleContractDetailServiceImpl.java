package com.shiro.steel.service.ipml;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.mapper.SaleContractDetailMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractDetailDto;
import com.shiro.steel.service.SaleContractDetailService;
@Service
public class SaleContractDetailServiceImpl extends ServiceImpl<SaleContractDetailMapper, SaleContractDetail> implements SaleContractDetailService{

	@Override
	public List<SaleContractDetailDto> findSaleContractDetailByPage(ParamsDto dto) {
		// TODO Auto-generated method stub
		List<SaleContractDetailDto> list = super.baseMapper.findSaleContractDetailByPage(dto);
        return list;
	}

	@Override
	public void deleteBatchNos(List<String> contractnos) {
		// TODO Auto-generated method stub
		super.baseMapper.deleteBatchNos(contractnos);
	}


	@Override
	public void batchDeliveryOrderUpdate(List<String> asList, List<String> saleDetailIdList) {
		// TODO Auto-generated method stub
		super.baseMapper.batchDeliveryOrderUpdate(asList,saleDetailIdList);
		
	}

	@Override
	public void batchProcessUpdate(List<String> asList, List<String> saleDetailIdList) {
		// TODO Auto-generated method stub
		super.baseMapper.batchProcessUpdate(asList,saleDetailIdList);
	}

	@Override
	public void updateByContractno(SaleContractDetail saleContractDetail) {
		// TODO Auto-generated method stub
		super.baseMapper.updateByContractno(saleContractDetail);
		
	}

	@Override
	public void batchTransportOrderUpdate(List<String> asList, List<String> saleDetailIdList) {
		// TODO Auto-generated method stub
		super.baseMapper.batchTransportOrderUpdate(asList,saleDetailIdList);
	}

	
}
