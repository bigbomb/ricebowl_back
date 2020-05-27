package com.shiro.steel.mapper;

import java.util.List;

import com.shiro.steel.pojo.dto.DeliveryOrderDetailPurDto;
import com.shiro.steel.pojo.dto.SaleContractDto;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.entity.DeliveryOrder;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;
import com.shiro.steel.pojo.vo.DeliveryOrderVo;

public interface DeliveryOrderMapper extends BaseMapper<DeliveryOrder>{

	List<DeliveryOrderVo> findByPage(Page<DeliveryOrderVo> page, @Param("dto")ParamsDto dto, @Param("memberId")String memberId,
			@Param("createby") String createby, @Param("startTime")String startTime, @Param("endTime")String endTime);

	Integer updateBatchByDeliveryOrder( List<DeliveryOrder> deliveryOrderList);

	List<SaleContractDto> selectByDeliList(@Param("asList") List<String> asList);

    void batchUpdateBalance(List<DeliveryOrderDetailPurDto> dlolist);
}