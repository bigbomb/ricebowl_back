package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.ProcessOrder;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.ProcessOrderVo;

public interface ProcessOrderService extends IService<ProcessOrder>{
    @Transactional
	Boolean addProcessOrder(ProcessOrderVo processOrderVo,Integer contractId);

	List<ProcessOrderVo> findByPage(Page<ProcessOrderVo> page, ParamsDto dto, String memberId, String createby, String startTime, String endTime);


}
