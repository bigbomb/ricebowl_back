package com.shiro.steel.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.TransportOrder;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.TransportOrderDto;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderVo;

public interface TransportOrderService extends IService<TransportOrder>{

	Boolean addTransportOrder(TransportOrderVo transportOrderVo);

	List<TransportOrderDetailVo> findDetailByPageList(ParamsDto dto, String memberId, String transportNo);

	List<TransportOrderDto> findTransportOrderByPage(Page<TransportOrderDto> page, ParamsDto dto, String createby,
			 String memberId,String carrier, String startTimeString, String endTimeString);

}
