package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shiro.steel.entity.Carriers;
import com.shiro.steel.entity.TransportOrderDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.TransportOrderDetailDto;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TransportOrderDetailMapper extends BaseMapper<TransportOrderDetail>{
    int deleteByPrimaryKey(Integer id);

    TransportOrderDetail selectByPrimaryKey(Integer id);

    List<TransportOrderDetail> selectAll();

    int updateByPrimaryKey(TransportOrderDetail record);

	List<TransportOrderDetailDto> findDetailByPageList(@Param("dto") ParamsDto dto, @Param("memberId") String memberId, @Param("transportNo") String transportNo);

	void deleteBatchTransportOrderNos(List<String> asList);
}