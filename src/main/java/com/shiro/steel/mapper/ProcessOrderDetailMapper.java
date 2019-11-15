package com.shiro.steel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.ProcessOrderDetail;

public interface ProcessOrderDetailMapper extends BaseMapper<ProcessOrderDetail>{
    int deleteByPrimaryKey(Integer id);

    ProcessOrderDetail selectByPrimaryKey(Integer id);

    List<ProcessOrderDetail> selectAll();

    int updateByPrimaryKey(ProcessOrderDetail record);

	void deleteBatchProcessNos(@Param("list")List<String> asList);
}