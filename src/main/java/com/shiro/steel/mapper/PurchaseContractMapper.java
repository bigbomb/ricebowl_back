package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.entity.PurchaseContract;
import com.shiro.steel.entity.PurchaseContractDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PurchaseContractDto;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PurchaseContractMapper extends BaseMapper<PurchaseContract>{
    int deleteByPrimaryKey(Integer id);

    PurchaseContract selectByPrimaryKey(Integer id);

    List<PurchaseContract> selectAll();

    int updateByPrimaryKey(PurchaseContract record);

	List<PurchaseContractDto> findPurchaseContractByPage(Page<PurchaseContractDto> page, @Param("dto") ParamsDto dto);
	
	List<PurchaseContractDto> findPurchaseContractByStatusPage(Page<PurchaseContractDto> page, @Param("dto") ParamsDto dto,@Param("statusTab")String statusTab,@Param("invoicestatus") String invoiceStatus, 
			@Param("createby") String createby,@Param("startTime") String startTime,@Param("endTime") String endTime);
}