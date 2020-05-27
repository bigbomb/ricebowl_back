package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.PurchaseContractChange;
import java.util.List;

public interface PurchaseContractChangeMapper extends BaseMapper<PurchaseContractChange> {
    int deleteByPrimaryKey(Integer id);

    PurchaseContractChange selectByPrimaryKey(Integer id);

    List<PurchaseContractChange> selectAll();

    int updateByPrimaryKey(PurchaseContractChange record);
}