package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.Carriers;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.CarriersVo;

public interface CarriersService extends IService<Carriers>{

	@Transactional
	Integer addCarriers(CarriersVo carriersVo);

	List<CarriersVo> findByPage(Page<CarriersVo> page, ParamsDto dto, String memberId);
}
