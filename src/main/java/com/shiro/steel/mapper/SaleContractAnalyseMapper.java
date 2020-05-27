package com.shiro.steel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractAnalyseDto;
import com.shiro.steel.pojo.dto.SaleContractDto;

public interface SaleContractAnalyseMapper {

	List<SaleContractAnalyseDto> selectList(@Param("deliveryno") String deliveryno);

}
