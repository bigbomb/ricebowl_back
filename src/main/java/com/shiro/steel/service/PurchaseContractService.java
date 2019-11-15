package com.shiro.steel.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.entity.PurchaseContract;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PurchaseContractDto;
import com.shiro.steel.pojo.vo.PurchaseContractVo;

public interface PurchaseContractService extends IService<PurchaseContract>{

	List<PurchaseContractDto> findPurchaseContractByPage(Page<PurchaseContractDto> page, ParamsDto dto);
	
	List<PurchaseContractDto> findPurchaseContractByStatusPage(Page<PurchaseContractDto> page, ParamsDto dto,String statusTab,String invoiceStatus, String createby, String startTimeString, String endTimeString);
	@Transactional
	Object addContract(PurchaseContractVo purchaseContractVo);
	@Transactional
	Integer delBatchIds(List<String> asList, List<String> asList2);
	@Transactional
	Integer updateByVerify(Integer id, String purchaseno, String memberId);
	@Transactional
	Boolean copyContract(String contractno);

}
