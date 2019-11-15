package com.shiro.steel.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.WarehouseInfo;
import com.shiro.steel.pojo.dto.ParamsDto;

public interface WarehouseInfoService extends IService<WarehouseInfo> {

	 List<WarehouseInfo> findByPage(Page<WarehouseInfo> page, ParamsDto dto, String memberId) ;


}
