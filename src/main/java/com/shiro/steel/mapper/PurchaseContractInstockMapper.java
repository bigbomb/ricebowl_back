package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.entity.ProcessOrder;
import com.shiro.steel.entity.PurchaseContractInstock;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PurchaseContractDto;
import com.shiro.steel.pojo.dto.PurchaseInstockDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseContractInstockMapper extends BaseMapper<PurchaseContractInstock> {
    int deleteByPrimaryKey(Integer id);


    PurchaseContractInstock selectByPrimaryKey(Integer id);

    List<PurchaseContractInstock> selectAll();

    int updateByPrimaryKey(PurchaseContractInstock record);

    List<PurchaseInstockDto> findPurchaseInstockByStatusPage(Page<PurchaseInstockDto> page, @Param("dto") ParamsDto dto, @Param("statusTab")String statusTab, @Param("invoicestatus") String invoiceStatus,
                                                              @Param("createby") String createby, @Param("startTime") String startTime, @Param("endTime") String endTime);
}