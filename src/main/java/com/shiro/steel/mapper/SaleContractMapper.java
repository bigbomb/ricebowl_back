package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractDto;
import com.shiro.steel.pojo.dto.UserDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SaleContractMapper extends BaseMapper<SaleContract> {
	/**
     * @desc: 查询合同
     *
     * @param page 分页
     * @param dto 参数dto
     * @author: jwy
	 * @param string 
     * @date: 2017/12/19
     */
    List<SaleContractDto> findSaleContractByPage(Pagination page,@Param("dto") ParamsDto dto, @Param("searchDate")Integer searchDate);
    
    int deleteByPrimaryKey(Integer id);

    SaleContract selectByPrimaryKey(Integer id);

    List<SaleContract> selectAll();

    int updateByPrimaryKey(SaleContract record);

	Boolean updateByParam(SaleContract saleContract);

	List<SaleContractDto> findSaleContractByStatusPage(Pagination page,@Param("dto") ParamsDto dto, @Param("searchDate")Integer searchDate,
			@Param("statusTab")String statusTab, @Param("createby") String createby,@Param("contracttype") String contracttype,@Param("invoicestatus") String invoiceStatus, @Param("startTime")String startTimeString,@Param("endTime") String endTimeString, @Param("verifyBy") String verifyBy);

	Boolean updateByContract(SaleContract saleContract);

	Integer batchWeigtAmountUpdate(@Param("finaList") List<SaleContractDto> finaList);


}