package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.entity.Carriers;
import com.shiro.steel.entity.TransportOrder;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractDto;
import com.shiro.steel.pojo.dto.TransportOrderDto;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TransportOrderMapper extends BaseMapper<TransportOrder>{
    int deleteByPrimaryKey(Integer id);

    TransportOrder selectByPrimaryKey(Integer id);

    List<TransportOrder> selectAll();

    int updateByPrimaryKey(TransportOrder record);

	List<TransportOrderDetailVo> findDetailByPageList(@Param("memberId") String memberId, @Param("transportNo") String transportNo);

	List<TransportOrderDto> findTransportOrderByPage(Page<TransportOrderDto> page, @Param("dto")ParamsDto dto, @Param("createby") String createby,
			@Param("memberId") String memberId, @Param("carrier") String carrier, @Param("startTime") String startTimeString,  @Param("endTime") String endTimeString);

    List<TransportOrderDto> selectListBytransport(Page<SaleContractDto> page, @Param("dto")ParamsDto dto, @Param("createby")String createby,
                                                  @Param("startTime")String startTimeString, @Param("endTime")String endTimeString);
}