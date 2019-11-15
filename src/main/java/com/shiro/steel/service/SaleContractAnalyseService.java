package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.pojo.dto.CustomerInfoDto;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractAnalyseDto;
import com.shiro.steel.pojo.dto.SaleContractDto;
import com.shiro.steel.pojo.vo.CustomerInfoVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
public interface SaleContractAnalyseService  {

	List<SaleContractAnalyseDto> selectList(Page<SaleContractDto> page, ParamsDto dto, String createby,
			String startTimeString, String endTimeString);


    
}
