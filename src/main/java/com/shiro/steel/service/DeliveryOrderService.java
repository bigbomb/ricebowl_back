package com.shiro.steel.service;

import java.util.List;

import com.shiro.steel.pojo.dto.DeliveryOrderDetailPurDto;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.DeliveryOrder;
import com.shiro.steel.entity.DeliveryOrderDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;
import com.shiro.steel.pojo.vo.DeliveryOrderVo;
import com.shiro.steel.pojo.vo.ProcessOrderVo;

public interface DeliveryOrderService extends IService<DeliveryOrder>{

	@Transactional(rollbackFor = Exception.class)
	Boolean addDeliveryOrder(DeliveryOrderVo deliveryOrderVo);

	List<DeliveryOrderVo> findByPage(Page<DeliveryOrderVo> page, ParamsDto dto, String memberId, String createby, String startTimeString, String endTimeString);

	List<DeliveryOrderDetail> findDetailByPageList(ParamsDto dto, String memberId,String deliveryNo);
	@Transactional(rollbackFor = Exception.class)
	Boolean delDeliveryOrder(ParamsDto dto, String[] deliveryOrderNos, String[] saleContractNos);
	@Transactional(rollbackFor = Exception.class)
	Integer updateBatchByDeliveryOrder(List<DeliveryOrder> deliveryOrderList);

	@Transactional(rollbackFor = Exception.class)
    void batchUpdateBalance(List<DeliveryOrderDetailPurDto> dlolist);
}
