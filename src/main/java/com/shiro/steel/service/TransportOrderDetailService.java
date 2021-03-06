package com.shiro.steel.service;

import java.util.List;

import com.shiro.steel.pojo.dto.TransportOrderDetailDto;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.TransportOrderDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;

public interface TransportOrderDetailService extends IService<TransportOrderDetail>{

	List<TransportOrderDetailDto> findDetailByPageList(ParamsDto dto, String memberId, String transportNo);
	@Transactional
	void deleteBatchTransportOrderNos(List<String> asList);



}
