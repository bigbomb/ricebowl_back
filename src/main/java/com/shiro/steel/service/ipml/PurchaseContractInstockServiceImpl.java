package com.shiro.steel.service.ipml;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.PurchaseContractInstock;
import com.shiro.steel.mapper.PurchaseContractInstockMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PurchaseInstockDto;
import com.shiro.steel.service.PurchaseContractInstockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseContractInstockServiceImpl extends ServiceImpl<PurchaseContractInstockMapper, PurchaseContractInstock> implements PurchaseContractInstockService {
    @Override
    public void updateByPrimaryKey(PurchaseContractInstock purchaseContractInstock) {

    }

    @Override
    public List<PurchaseInstockDto> findPurchaseInstockByStatusPage(Page<PurchaseInstockDto> page, ParamsDto dto, String statusTab, String invoiceStatus, String createby, String startTime, String endTime) {
        return super.baseMapper.findPurchaseInstockByStatusPage(page, dto,statusTab,invoiceStatus,createby,startTime,endTime);
    }
}
