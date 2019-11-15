package com.shiro.steel.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shiro.steel.entity.Attribute;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author jwy
 * @since 2017-12-26
 */
public interface AttributeMapper extends BaseMapper<Attribute> {
    /**
     * @desc: 查询属性
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    List<Attribute> findAttributesByPage(Pagination page, @Param("dto") ParamsDto dto);
}