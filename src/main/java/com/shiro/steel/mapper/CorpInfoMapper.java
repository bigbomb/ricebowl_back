package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.Attribute;
import com.shiro.steel.entity.CorpInfo;
import java.util.List;

public interface CorpInfoMapper extends BaseMapper<CorpInfo> {
    int deleteByPrimaryKey(Integer id);


    CorpInfo selectByPrimaryKey(Integer id);

    List<CorpInfo> selectAll();

    int updateByPrimaryKey(CorpInfo record);
}