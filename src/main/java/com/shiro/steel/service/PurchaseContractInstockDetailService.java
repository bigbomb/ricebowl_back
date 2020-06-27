package com.shiro.steel.service;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.PurchaseContractInstock;
import com.shiro.steel.entity.PurchaseContractInstockDetail;

public interface PurchaseContractInstockDetailService extends IService<PurchaseContractInstockDetail> {
    void updateByPrimaryKey(PurchaseContractInstockDetail purchaseContractInstockDetail);
}
