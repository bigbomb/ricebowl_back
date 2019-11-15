package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.pojo.dto.CustomerInfoDto;
import com.shiro.steel.pojo.dto.ParamsDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CustomerInfoMapper extends BaseMapper<CustomerInfo>{
    int deleteByPrimaryKey(Integer id);

    CustomerInfo selectByPrimaryKey(Integer id);

    List<CustomerInfo> selectAll();

    int updateByPrimaryKey(CustomerInfo record);

	List<CustomerInfoDto> findByPage(Page<CustomerInfoDto> page,@Param("dto") ParamsDto dto, @Param("memberId")String memberId, @Param("createby")String createby);

	Integer getIdAfterInsert(CustomerInfo customerInfo);
}