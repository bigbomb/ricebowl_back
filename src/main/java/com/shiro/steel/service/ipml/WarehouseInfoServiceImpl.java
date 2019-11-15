package com.shiro.steel.service.ipml;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.WarehouseInfo;
import com.shiro.steel.mapper.WarehouseInfoMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.service.WarehouseInfoService;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
@Service
public class WarehouseInfoServiceImpl extends ServiceImpl<WarehouseInfoMapper, WarehouseInfo> implements WarehouseInfoService {

	@Override
	public List<WarehouseInfo> findByPage(Page<WarehouseInfo> page, ParamsDto dto, String memberId) {
		// TODO Auto-generated method stub
		return super.baseMapper.findByPage(page,dto,memberId);
	}
	
}
