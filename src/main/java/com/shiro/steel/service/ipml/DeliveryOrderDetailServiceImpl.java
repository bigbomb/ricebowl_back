package com.shiro.steel.service.ipml;

import java.util.List;

import com.shiro.steel.entity.Stock;
import com.shiro.steel.pojo.dto.DeliveryOrderDetailPurDto;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.DeliveryOrderDetail;
import com.shiro.steel.mapper.DeliveryOrderDetailMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;
import com.shiro.steel.service.DeliveryOrderDetailService;

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

	@Override
	public DeliveryOrderDetailPurDto selectByPurId(Stock stock) {
		DeliveryOrderDetailPurDto dodv = super.baseMapper.selectByPurId(stock);
		return dodv;
	}


}
