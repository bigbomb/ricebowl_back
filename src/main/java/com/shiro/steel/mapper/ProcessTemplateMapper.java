package com.shiro.steel.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.ProcessTemplate;

public interface ProcessTemplateMapper extends BaseMapper<ProcessTemplate>{
    int deleteByPrimaryKey(Integer id);


    ProcessTemplate selectByPrimaryKey(Integer id);

    List<ProcessTemplate> selectAll();

    int updateByPrimaryKey(ProcessTemplate record);
}