package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.entity.SaleContractTerm;
import java.util.List;

public interface SaleContractTermMapper extends BaseMapper<SaleContractTerm>{
    int deleteByPrimaryKey(Integer id);

    SaleContractTerm selectByPrimaryKey(Integer id);

    List<SaleContractTerm> selectAll();

    int updateByPrimaryKey(SaleContractTerm record);
}