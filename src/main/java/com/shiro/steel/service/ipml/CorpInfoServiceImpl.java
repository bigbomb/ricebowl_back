package com.shiro.steel.service.ipml;

import com.shiro.steel.config.StorageConfig;
import com.shiro.steel.utils.CommonUtil;
import com.shiro.steel.utils.QcloudCosUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.entity.CorpInfo;
import com.shiro.steel.mapper.CorpInfoMapper;
import com.shiro.steel.service.CorpInfoService;
import com.shiro.steel.utils.ResultUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class CorpInfoServiceImpl extends ServiceImpl<CorpInfoMapper, CorpInfo> implements CorpInfoService {
	 /**
     * @desc: 新增属性
     *
     * @author: jwy
     * @date: 2017/12/27
     */
	 @Autowired
	 private QcloudCosUtils qcloudCosUtils;

	 @Autowired
	 private StorageConfig storageConfig;
	 @Transactional
    public Object saveCorpInfo(CorpInfo corpInfo, MultipartFile picFile) throws IOException {
    	if(corpInfo.getId()!=null)
		{
			File savedFile = CommonUtil.MultipartFileToFile(picFile);
			String sealurl = qcloudCosUtils.uploadFile(savedFile,storageConfig.getDir());
			corpInfo.setContractsealurl(sealurl);
		   baseMapper.updateById(corpInfo);
		}
    	else
    	{
		   baseMapper.insert(corpInfo);
    	}
      
        return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    }

	
}