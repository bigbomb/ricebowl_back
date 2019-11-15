package com.shiro.steel.service;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.CorpInfo;
import com.shiro.steel.entity.SaleContract;

public interface CorpInfoService extends IService<CorpInfo>{
	@Transactional
	Object saveCorpInfo(CorpInfo corpInfo);

}
