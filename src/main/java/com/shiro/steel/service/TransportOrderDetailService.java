package com.shiro.steel.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.TransportOrder;
import com.shiro.steel.entity.TransportOrderDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderVo;

public interface TransportOrderDetailService extends IService<TransportOrderDetail>{

	List<TransportOrderDetailVo> findDetailByPageList(ParamsDto dto, String memberId, String transportNo);

	void deleteBatchTransportOrderNos(List<String> asList);



}
