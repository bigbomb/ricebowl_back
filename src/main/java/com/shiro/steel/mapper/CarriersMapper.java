package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.entity.Carriers;
import com.shiro.steel.entity.CorpInfo;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.CarriersVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CarriersMapper extends BaseMapper<Carriers>{
    int deleteByPrimaryKey(Integer id);

    Carriers selectByPrimaryKey(Integer id);

    List<Carriers> selectAll();

    int updateByPrimaryKey(Carriers record);

	List<CarriersVo> findByPage(Page<CarriersVo> page, @Param("dto") ParamsDto dto,@Param("memberId") String memberId);
}