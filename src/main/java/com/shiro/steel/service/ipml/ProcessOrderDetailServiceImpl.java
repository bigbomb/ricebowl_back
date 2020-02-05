package com.shiro.steel.service.ipml;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.ProcessOrder;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.mapper.ProcessOrderDetailMapper;
import com.shiro.steel.mapper.ProcessOrderMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.ProcessOrderDetailDto;
import com.shiro.steel.pojo.vo.ProcessOrderVo;
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
public class ProcessOrderDetailServiceImpl extends ServiceImpl<ProcessOrderDetailMapper, ProcessOrderDetail> implements ProcessOrderDetailService {

	@Override
	public void deleteBatchProcessNos(List<String> asList) {
		// TODO Auto-generated method stub
		super.baseMapper.deleteBatchProcessNos(asList);
	}

	@Override
	public List<ProcessOrderDetailDto> selectList(String processNo) {
		// TODO Auto-generated method stub
		return super.baseMapper.selectPodList(processNo);
		
	}

	

    
}
