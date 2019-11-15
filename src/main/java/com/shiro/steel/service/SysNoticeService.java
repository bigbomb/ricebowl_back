package com.shiro.steel.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.SysNotice;
import com.shiro.steel.pojo.dto.ParamsDto;

public interface SysNoticeService extends IService<SysNotice>{

	List<SysNotice> findSysNoticeByPage(String memberId);

	List<SysNotice> findNoticeByPage(Page<SysNotice> page, ParamsDto dto);

}
