package com.shiro.steel.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.api.base.BaseApi;
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.entity.SaleContractTerm;
import com.shiro.steel.pojo.vo.CustomerInfoVo;
import com.shiro.steel.pojo.vo.SaleContractTermVo;
import com.shiro.steel.service.SaleContractService;
import com.shiro.steel.service.SaleContractTermService;
import com.shiro.steel.utils.ResultUtil;
@RestController
@RequestMapping(value = "SaleContractTermApi/v1")
public class SaleContractTermApi extends BaseApi{
	@Autowired
	private SaleContractTermService saleContractTermService;
	@Autowired
	private SaleContractService saleContractService;
	
	@RequestMapping(value = "/addTerm",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addTerm(@Valid SaleContractTermVo saleContractTermVo, BindingResult bindingResult) {
		SaleContract saleContract = new SaleContract();
		
		saleContract.setMemberid(saleContractTermVo.getMemberid());
		saleContract.setTermContent(saleContractTermVo.getContent());
		if (saleContractTermVo.getId()==null)
		{
			Boolean count = saleContractService.insert(saleContract);
	        if(count)
	        {
	        		return ResultUtil.result(EnumCode.OK.getValue(), "保存成功" );
	        }
	        else
	        {
	        	return ResultUtil.result(EnumCode.OK.getValue(), "保存失败" );
	        }
		}
		else
		{
			saleContract.setId(saleContractTermVo.getId());
			saleContractService.updateById(saleContract);
			return ResultUtil.result(EnumCode.OK.getValue(), "保存成功" );
		}
		
    }
	
	@RequestMapping(value = "/getTerm",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object getTerm(String memberId,String id) {
		SaleContract saleContract = new SaleContract();
		saleContract.setId(Integer.parseInt(id));
		saleContract.setMemberid(memberId);
		EntityWrapper<SaleContract> wrapper = new EntityWrapper<SaleContract>(saleContract);
		saleContract = saleContractService.selectOne(wrapper);
		if (saleContract!=null)
		{
			return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",saleContract );
		}
		else
		{
			return ResultUtil.result(EnumCode.GONE.getValue(), "",null );
		}
         
       
    }
}
