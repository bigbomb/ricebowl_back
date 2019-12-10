package com.shiro.steel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.entity.DeliveryOrderDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;

public interface DeliveryOrderDetailMapper extends BaseMapper<DeliveryOrderDetail>{

	void deleteBatchDeliveryOrderNos(List<String> asList);

	List<DeliveryOrderDetailVo> findDetailByPageList(@Param("memberId")String memberId,
			@Param("deliveryNos")String[] deliveryNos);
  
}