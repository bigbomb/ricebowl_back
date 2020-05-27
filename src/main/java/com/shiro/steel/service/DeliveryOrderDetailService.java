package com.shiro.steel.service;

import java.util.List;

import com.shiro.steel.entity.Stock;
import com.shiro.steel.pojo.dto.DeliveryOrderDetailPurDto;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.DeliveryOrderDetail;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;

public interface DeliveryOrderDetailService extends IService<DeliveryOrderDetail>{
	@Transactional
	void deleteBatchDeliveryOrderNos(List<String> asList);

	List<DeliveryOrderDetailVo> findDetailByPageList(ParamsDto dto, String memberId,
			String[] deliveryNos);

	DeliveryOrderDetailPurDto selectByPurId(Stock stock);
}
