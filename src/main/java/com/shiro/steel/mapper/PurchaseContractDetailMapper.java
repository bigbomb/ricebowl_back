package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.PurchaseContract;
import com.shiro.steel.entity.PurchaseContractDetail;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PurchaseContractDetailMapper extends BaseMapper<PurchaseContractDetail>{
    int deleteByPrimaryKey(Integer id);

    PurchaseContractDetail selectByPrimaryKey(Integer id);

    List<PurchaseContractDetail> selectAll();

    int updateByPrimaryKey(PurchaseContractDetail record);

	void deleteBatchNos(@Param("list")List<String> contractnos);

	void updateByDetail(PurchaseContractDetail purchaseContractDetail);
}