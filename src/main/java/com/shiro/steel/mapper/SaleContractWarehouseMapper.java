package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.SaleContractTerm;
import com.shiro.steel.entity.SaleContractWarehouse;
import java.util.List;

public interface SaleContractWarehouseMapper  extends BaseMapper<SaleContractWarehouse>{
    int deleteByPrimaryKey(Integer id);

    SaleContractWarehouse selectByPrimaryKey(Integer id);

    List<SaleContractWarehouse> selectAll();

    int updateByPrimaryKey(SaleContractWarehouse record);
}