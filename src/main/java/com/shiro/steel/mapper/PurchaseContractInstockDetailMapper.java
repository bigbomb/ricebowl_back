package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.PurchaseContractDetail;
import com.shiro.steel.entity.PurchaseContractInstockDetail;
import java.util.List;

public interface PurchaseContractInstockDetailMapper extends BaseMapper<PurchaseContractInstockDetail> {
    int deleteByPrimaryKey(Integer id);

    PurchaseContractInstockDetail selectByPrimaryKey(Integer id);

    List<PurchaseContractInstockDetail> selectAll();

    int updateByPrimaryKey(PurchaseContractInstockDetail record);
}