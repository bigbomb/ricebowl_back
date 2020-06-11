package com.shiro.steel.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.shiro.steel.utils.ResultUtil;
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
	@Transactional(rollbackFor = Exception.class)
	String addContract(PurchaseContractVo purchaseContractVo) throws Exception;
	@Transactional(rollbackFor = Exception.class)
	Integer delBatchIds(List<String> asList, List<String> asList2);
	@Transactional(rollbackFor = Exception.class)
	Integer updateByVerify(Integer id, String purchaseno, String memberId) throws ParseException;
	@Transactional(rollbackFor = Exception.class)
	Boolean copyContract(String contractno);

}
