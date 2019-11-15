package com.shiro.steel.api;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.api.base.BaseApi;
import com.shiro.steel.entity.CorpInfo;
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.entity.SaleDoc;
import com.shiro.steel.service.CorpInfoService;
import com.shiro.steel.service.SaleDocService;
import com.shiro.steel.utils.ResultUtil;

@RestController
@RequestMapping(value = "SaleDocApi/v1")
public class SaleDocApi extends BaseApi{
	
	@Autowired
	private SaleDocService saleDocService;
	
	@Autowired
	private CorpInfoService corpInfoService;
	/**
     * @desc: 新增报价单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/addSaleDoc" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addSaleDoc(@Valid SaleDoc saleDoc, BindingResult bindingResult) {
    	if(StringUtils.isEmpty(saleDoc.getId()))
    	{
    		saleDoc.setCrt(new Date());
    	}
    	else
    	{
    		saleDoc.setUpt(new Date());
    	}
    	CorpInfo corpInfo = new CorpInfo();
    	corpInfo.setMemberId(saleDoc.getMemberid());
   	    EntityWrapper<CorpInfo> eWrapper = new EntityWrapper<CorpInfo>(corpInfo);
        corpInfo = corpInfoService.selectOne(eWrapper);
        saleDoc.setCompanyname(corpInfo.getCompanyname());
    	Boolean status = saleDocService.insertOrUpdate(saleDoc);
    	if(status)
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    	}else
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "保存失败");
    	}
        
    }
    /**
     * @desc: 新增报价单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/getSaleDoc" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object getSaleDoc(@Valid SaleDoc saleDoc, BindingResult bindingResult) {
    	EntityWrapper<SaleDoc> eWrapper = new EntityWrapper<SaleDoc>(saleDoc);
    	SaleDoc newsaleDoc = saleDocService.selectOne(eWrapper);
    	return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",newsaleDoc);
    }
}
