package com.shiro.steel.service.ipml;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.entity.CorpInfo;
import com.shiro.steel.mapper.CorpInfoMapper;
import com.shiro.steel.service.CorpInfoService;
import com.shiro.steel.utils.ResultUtil;

@Service
public class CorpInfoServiceImpl extends ServiceImpl<CorpInfoMapper, CorpInfo> implements CorpInfoService {
	 /**
     * @desc: 新增属性
     *
     * @author: jwy
     * @date: 2017/12/27
     */
	 @Transactional
    public Object saveCorpInfo(CorpInfo corpInfo) {
    	if(corpInfo.getId()!=null)
		{
		   baseMapper.updateById(corpInfo);
		}
    	else
    	{
		   baseMapper.insert(corpInfo);
    	}
      
        return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    }

	
}