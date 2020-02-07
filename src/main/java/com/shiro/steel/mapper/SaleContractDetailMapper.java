package com.shiro.steel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractDetailDto;
import com.shiro.steel.pojo.dto.SaleContractDto;

public interface SaleContractDetailMapper extends BaseMapper<SaleContractDetail>{
    int deleteByPrimaryKey(Integer id);


    SaleContractDetail selectByPrimaryKey(Integer id);

    List<SaleContractDetail> selectAll();

    int updateByPrimaryKey(SaleContractDetail record);
    
    List<SaleContractDetailDto> findSaleContractDetailByPage(@Param("dto") ParamsDto dto);
    
	void deleteBatchNos(@Param("list")List<String> contractnos);

	void batchProcessUpdate(@Param("list")List<String> asList, @Param("sdlist") List<String> saleDetailIdList);


	void batchDeliveryOrderUpdate(@Param("list")List<String> asList, @Param("sdlist")List<String> saleDetailIdList);


	void updateByContractno(SaleContractDetail saleContractDetail);


	void batchTransportOrderUpdate(@Param("list")List<String> asList, @Param("sdlist")List<String> saleDetailIdList);
	
	List<SaleContractDto> selectByStockIdList(List<String> stockIdList);


	Boolean updateBatchByEntity(@Param("list")List<SaleContractDetail> saleContractDetailList);
}