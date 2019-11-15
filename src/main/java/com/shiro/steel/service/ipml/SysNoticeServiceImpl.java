package com.shiro.steel.service.ipml;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.SysNotice;
import com.shiro.steel.mapper.SysNoticeMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.service.SysNoticeService;

@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService{

	@Override
	public List<SysNotice> findSysNoticeByPage(String memberId) {
		return super.baseMapper.findSysNoticeByPage(memberId);
	}

	@Override
	public List<SysNotice> findNoticeByPage(Page<SysNotice> page, ParamsDto dto) {
		// TODO Auto-generated method stub
		return super.baseMapper.findNoticeByPage(page,dto);
	}

}

