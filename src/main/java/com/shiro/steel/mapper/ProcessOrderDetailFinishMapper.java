package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.ProcessOrderDetailFinish;
import java.util.List;

public interface ProcessOrderDetailFinishMapper extends BaseMapper<ProcessOrderDetailFinish> {
    int deleteByPrimaryKey(Integer id);

    ProcessOrderDetailFinish selectByPrimaryKey(Integer id);

    List<ProcessOrderDetailFinish> selectAll();

    int updateByPrimaryKey(ProcessOrderDetailFinish record);
}