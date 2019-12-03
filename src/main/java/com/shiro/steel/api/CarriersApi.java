package com.shiro.steel.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.api.base.BaseApi;
import com.shiro.steel.entity.Carriers;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.CarriersVo;
import com.shiro.steel.service.CarriersService;
import com.shiro.steel.utils.ResultUtil;

@RestController
@RequestMapping(value = "CarriersApi/v1")
public class CarriersApi extends BaseApi{
	
	@Autowired 
	private CarriersService carriersService ;
	

	/**
     * @desc: 新增报价单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/addCarrier" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addCarrier(@Validated CarriersVo carriersVo,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResultUtil.result(EnumCode.OK.getValue(), errorList.toString());
        	}
    	    Integer status =  carriersService.addCarriers(carriersVo);
    	   
    	    if (status==1)
    	     {
    	    	 return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    	     }else
    	     {
    	    	 return ResultUtil.result(EnumCode.OK.getValue(), "保存失败");
    	     }
    
    }
    
    
    @RequestMapping(value = "/findByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByPage(ParamsDto dto,String memberId){

        Page<CarriersVo> page = new Page<>(dto.getStartPage(),dto.getPageSize());
         List<CarriersVo> list = carriersService.findByPage(page, dto,memberId);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list, page.getTotal(),page.getPages());
    }
    
    
    @RequestMapping(value = "/findByNoPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByNoPage(ParamsDto dto,String memberId){
    	 Carriers carriers = new Carriers();
    	 carriers.setMemberid(memberId);
    	 EntityWrapper<Carriers> eWrapper = new EntityWrapper<Carriers>(carriers);
         List<Carriers> list = carriersService.selectList(eWrapper);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list);
    }
    
    
    
    @RequestMapping(value = "/delCarrierRow" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delCarrierRow(Integer id){
         Boolean status = carriersService.deleteById(id);
         if(status)
     	{
     		return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
     	}else
     	{
     		return ResultUtil.result(EnumCode.OK.getValue(), "删除失败");
     	}
    }
   
    
}
