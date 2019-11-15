package com.shiro.steel.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.Carriers;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.CarriersVo;

public interface CarriersService extends IService<Carriers>{

	Integer addCarriers(CarriersVo carriersVo);

	List<CarriersVo> findByPage(Page<CarriersVo> page, ParamsDto dto, String memberId);
}
