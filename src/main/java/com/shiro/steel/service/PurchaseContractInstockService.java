package com.shiro.steel.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.PurchaseContractInstock;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PurchaseContractDto;
import com.shiro.steel.pojo.dto.PurchaseInstockDto;

import java.util.List;

public interface PurchaseContractInstockService extends IService<PurchaseContractInstock> {
    void updateByPrimaryKey(PurchaseContractInstock purchaseContractInstock);

    List<PurchaseInstockDto> findPurchaseInstockByStatusPage(Page<PurchaseInstockDto> page, ParamsDto dto, String statusTab, String invoiceStatus, String createby, String startTimeString, String endTimeString);
}
