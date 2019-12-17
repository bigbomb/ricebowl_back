package com.shiro.steel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.ProcessOrderDetailFinish;
import com.shiro.steel.pojo.vo.ProcessDetailFinishVo;

/** 

* @author 作者 lujunjie: 

* @version 创建时间：Dec 17, 2019 10:22:03 AM 

* 类说明 

*/
public interface ProcessOrderDetailFinishService extends IService<ProcessOrderDetailFinish>{
	
	@Transactional
	Boolean addProcessOrderFinish(ProcessDetailFinishVo processDetailFinishVo);

	List<ProcessOrderDetailFinish> getProcessOrderFinish(String processNo);

}
