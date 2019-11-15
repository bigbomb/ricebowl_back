package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.pojo.dto.CustomerInfoDto;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.CustomerInfoVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
public interface CustomerInfoService extends IService<CustomerInfo> {

	List<CustomerInfoDto> findByPage(Page<CustomerInfoDto> page, ParamsDto dto, String memberId, String createby);
	@Transactional
	Object editCustomerStatus(String id, Integer type);
	@Transactional
	Object addCustomer(CustomerInfoVo customerInfoVo);
	@Transactional
	Object delCustomer(String[] ids);
	@Transactional
	Object bind(String companyPhone, String invitecode, String openId);

    
}
