package com.shiro.steel.service;

import java.util.List;

import com.shiro.steel.entity.DeliveryOrderDetail;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractDetailDto;
import com.shiro.steel.pojo.dto.SaleContractDto;


public interface SaleContractDetailService extends IService<SaleContractDetail>{
	List<SaleContractDetailDto> findSaleContractDetailByPage(ParamsDto dto);
	 @Transactional
	void deleteBatchNos(List<String> contactnos);
	 @Transactional
	void batchProcessUpdate(List<String> asList, List<String> saleDetailIdList);
//	 @Transactional
//	void batchDeliveryOrderUpdate(List<String> asList, List<String> saleDetailIdList);
	 @Transactional
	void updateByContractno(SaleContractDetail saleContractDetail);
	 @Transactional
	void batchTransportOrderUpdate(List<String> asList, List<String> saleDetailIdList);

	 List<SaleContractDto> selectByStockIdList(List<String> stockIdList);
	 @Transactional
	Boolean updateBatchByEntity(List<SaleContractDetail> saleContractDetailList);
	@Transactional
    void updateBatchBySd(List<SaleContractDetail> saleContractDetailList);
	@Transactional
	void batchDeliveryOrderUpdate(List<DeliveryOrderDetail> deliveryOrderDetailListExt);
}
