package com.shiro.steel.service.ipml;

import java.util.List;

import com.shiro.steel.pojo.dto.TransportOrderDetailDto;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.TransportOrderDetail;
import com.shiro.steel.mapper.TransportOrderDetailMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;
import com.shiro.steel.service.TransportOrderDetailService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
@Service
public class TransportOrderDetailServiceImpl extends ServiceImpl<TransportOrderDetailMapper, TransportOrderDetail> implements TransportOrderDetailService {

	@Override
	public List<TransportOrderDetailDto> findDetailByPageList(ParamsDto dto, String memberId, String transportNo) {
		// TODO Auto-generated method stub
		List<TransportOrderDetailDto> list = super.baseMapper.findDetailByPageList(dto,memberId,transportNo);
        return list;
	}

	@Override
	public void deleteBatchTransportOrderNos(List<String> asList) {
		// TODO Auto-generated method stub
		super.baseMapper.deleteBatchTransportOrderNos(asList);
	}

	

    
}
