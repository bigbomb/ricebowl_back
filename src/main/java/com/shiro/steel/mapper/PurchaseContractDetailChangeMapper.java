package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.PurchaseContractDetailChange;
import java.util.List;

public interface PurchaseContractDetailChangeMapper extends BaseMapper<PurchaseContractDetailChange> {
    int deleteByPrimaryKey(Integer id);

    PurchaseContractDetailChange selectByPrimaryKey(Integer id);

    List<PurchaseContractDetailChange> selectAll();

    int updateByPrimaryKey(PurchaseContractDetailChange record);
}