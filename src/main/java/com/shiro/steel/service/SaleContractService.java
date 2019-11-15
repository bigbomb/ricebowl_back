package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractDto;
import com.shiro.steel.pojo.vo.ContractVo;


public interface SaleContractService extends IService<SaleContract>{
	   /**
     * @desc: 查询合同
     *
     * @param dto 参数dto
     * @author: jwy
	 * @param searchDate 
     * @date: 2017/12/19
     */

	List<SaleContractDto> findSaleContractByPage(Page<SaleContractDto> page, ParamsDto dto, Integer searchDate);
	  @Transactional
	 Object addContract(ContractVo contractVo);
	  @Transactional
	  Integer delBatchIds(List<String> ids,List<String> contactnos);
	  @Transactional
	  Boolean copyContract(String contractno);
	  @Transactional
	  Boolean updateByParam(SaleContract saleContract);
	  List<SaleContractDto> findSaleContractByPage(Page<SaleContractDto> page, ParamsDto dto, Integer searchDate,
			String statusTab, String createby,String contracttype, String invoiceStatus, String startTimeString, String endTimeString, String verifyBy);
	  @Transactional
	  Boolean update(SaleContract saleContract, String contractno);
	  @Transactional
	  Boolean updateByContract(SaleContract saleContract);

}
