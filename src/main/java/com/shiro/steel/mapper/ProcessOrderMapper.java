package com.shiro.steel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.steel.entity.ProcessOrder;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.ProcessOrderVo;

public interface ProcessOrderMapper extends BaseMapper<ProcessOrder>{
    int deleteByPrimaryKey(Integer id);

    ProcessOrder selectByPrimaryKey(Integer id);

    List<ProcessOrder> selectAll();

    int updateByPrimaryKey(ProcessOrder record);

	List<ProcessOrderVo> findByPage(Pagination page, @Param("dto") ParamsDto dto, @Param("memberId")String memberId, 
			@Param("createby") String createby,@Param("startTime") String startTime, @Param("endTime") String endTime);
}