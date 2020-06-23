package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.ProcessOrder;
import com.shiro.steel.entity.PurchaseContractInstock;
import java.util.List;

public interface PurchaseContractInstockMapper extends BaseMapper<PurchaseContractInstock> {
    int deleteByPrimaryKey(Integer id);


    PurchaseContractInstock selectByPrimaryKey(Integer id);

    List<PurchaseContractInstock> selectAll();

    int updateByPrimaryKey(PurchaseContractInstock record);
}