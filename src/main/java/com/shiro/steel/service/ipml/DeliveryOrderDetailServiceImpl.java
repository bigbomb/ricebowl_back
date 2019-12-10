package com.shiro.steel.service.ipml;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.DeliveryOrderDetail;
import com.shiro.steel.entity.ProcessOrder;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.mapper.DeliveryOrderDetailMapper;
import com.shiro.steel.mapper.ProcessOrderDetailMapper;
import com.shiro.steel.mapper.ProcessOrderMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;
import com.shiro.steel.pojo.vo.ProcessOrderVo;
import com.shiro.steel.service.DeliveryOrderDetailService;
import com.shiro.steel.service.ProcessOrderDetailService;
import com.shiro.steel.service.ProcessOrderService;
import com.shiro.steel.utils.GeneratorUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
@Service
public class DeliveryOrderDetailServiceImpl extends ServiceImpl<DeliveryOrderDetailMapper, DeliveryOrderDetail> implements DeliveryOrderDetailService {

	@Override
	public void deleteBatchDeliveryOrderNos(List<String> asList) {
		// TODO Auto-generated method stub
		super.baseMapper.deleteBatchDeliveryOrderNos(asList);
	}

	@Override
	public List<DeliveryOrderDetailVo> findDetailByPageList(ParamsDto dto,
			String memberId, String[] deliveryNos) {
		// TODO Auto-generated method stub
		List<DeliveryOrderDetailVo> list = super.baseMapper.findDetailByPageList(memberId,deliveryNos);
        return list;
	}


	

    
}
