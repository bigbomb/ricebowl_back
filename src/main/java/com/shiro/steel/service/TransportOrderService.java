package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.TransportOrder;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.TransportOrderDto;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderVo;

public interface TransportOrderService extends IService<TransportOrder>{
	@Transactional
	Boolean addTransportOrder(TransportOrderVo transportOrderVo);

	List<TransportOrderDetailVo> findDetailByPageList(ParamsDto dto, String memberId, String transportNo);

	List<TransportOrderDto> findTransportOrderByPage(Page<TransportOrderDto> page, ParamsDto dto, String createby,
			 String memberId,String carrier, String startTimeString, String endTimeString);
	@Transactional
	Boolean delTransportOrder(ParamsDto dto, String[] transportOrderNos, String[] saleContractNos, String[] deliveryOrderNos);

}
