package com.shiro.steel.service.ipml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.Carriers;
import com.shiro.steel.mapper.CarriersMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.CarriersVo;
import com.shiro.steel.service.CarriersService;

@Service
public class CarriersServiceImpl extends ServiceImpl<CarriersMapper, Carriers> implements CarriersService {

	@Override
	public Integer addCarriers(CarriersVo carriersVo) {
		Carriers carriers = new Carriers();
 	    BeanCopier copier = BeanCopier.create(CarriersVo.class, Carriers.class, false);
 	    copier.copy(carriersVo, carriers, null);
 	    Integer result = null;
		if(carriersVo.getId()!=null)
		{
			carriers.setUpt(new Date());
			result = super.baseMapper.updateById(carriers);
		}
		else
		{
			carriers.setCrt(new Date());
			result = super.baseMapper.insert(carriers);
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public List<CarriersVo> findByPage(Page<CarriersVo> page, ParamsDto dto, String memberId) {
		// TODO Auto-generated method stub
		List<CarriersVo> carriersList = super.baseMapper.findByPage(page,dto,memberId);
		return carriersList;
	}

  

	
}