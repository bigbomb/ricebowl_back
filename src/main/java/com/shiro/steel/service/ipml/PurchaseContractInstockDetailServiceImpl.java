package com.shiro.steel.service.ipml;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.PurchaseContractInstockDetail;
import com.shiro.steel.mapper.PurchaseContractInstockDetailMapper;
import com.shiro.steel.service.PurchaseContractInstockDetailService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseContractInstockDetailServiceImpl extends ServiceImpl<PurchaseContractInstockDetailMapper, PurchaseContractInstockDetail> implements PurchaseContractInstockDetailService {
    @Override
    public void updateByPrimaryKey(PurchaseContractInstockDetail purchaseContractInstockDetail) {

    }
}
