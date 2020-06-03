package com.shiro.steel.service;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.CorpInfo;
import com.shiro.steel.entity.SaleContract;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CorpInfoService extends IService<CorpInfo>{
	@Transactional
	Object saveCorpInfo(CorpInfo corpInfo, MultipartFile picFile) throws IOException;

}
