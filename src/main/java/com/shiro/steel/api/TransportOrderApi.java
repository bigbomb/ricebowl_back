package com.shiro.steel.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
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
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.entity.TransportOrderDetail;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.TransportOrderDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderVo;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.service.TransportOrderDetailService;
import com.shiro.steel.service.TransportOrderService;
import com.shiro.steel.utils.ResultUtil;

@RestController
@RequestMapping(value = "TransportOrderApi/v1")
public class TransportOrderApi extends BaseApi{
	
    @Autowired
    private TransportOrderDetailService transportOrderDetailService;
    
    @Autowired
    private TransportOrderService transportOrderService;
    
    @Autowired
    private SaleContractDetailService saleContractDetailService;
	/**
     * @desc: 新增报价单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/addTransportOrder" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addDeliveryOrder(@Validated TransportOrderVo transportOrderVo,BindingResult bindingResult,String[] ids) {
    	if (bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResultUtil.result(EnumCode.OK.getValue(), errorList.toString());
        	}
    	    BigDecimal totalWeight = new BigDecimal(0);
    	    for(String id:ids)
    	    {
    	    	SaleContractDetail saleContractDetail = new SaleContractDetail();
    	    	saleContractDetail = saleContractDetailService.selectById(Integer.parseInt(id));
    	    	BigDecimal weight = saleContractDetail.getFinalweight();
    	    	totalWeight = totalWeight.add(weight).setScale(2, BigDecimal.ROUND_HALF_UP);
    	    }
    	    transportOrderVo.setTransportweight(totalWeight);
    	    Boolean status =  transportOrderService.addTransportOrder(transportOrderVo);
    	   
    	    if (status)
    	     {
    	    	 return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    	     }else
    	     {
    	    	 return ResultUtil.result(EnumCode.OK.getValue(), "保存失败");
    	     }
    
    }
    
    
    @RequestMapping(value = "/findDetailByPageList" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findDetailByPageList(ParamsDto dto,String memberId,String transportNo){
//        Page<DeliveryOrderDetailVo> page = new Page<>(dto.getStartPage(),dto.getPageSize());
         List<TransportOrderDetailVo> list = transportOrderDetailService.findDetailByPageList(dto,memberId,transportNo);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list);
    }
    
    @RequestMapping(value = "/findByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByPage(ParamsDto dto,String memberId,String carrier,String startTime,String endTime) throws ParseException{
    	UserInfoDto userInfoDto = new UserInfoDto();
    	Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
    	String createby = null;
    	Integer type = userInfoDto.getType();
    	if(type==1)
    	{
    		
    	}
    	else if(type==2)
    	{
    		createby = userInfoDto.getUsername();
    	}
    	Date startTimeDate = null;
        Date endTimeDate = null;
        String startTimeString = "";
        String endTimeString = "";		
        if(!StringUtils.isEmpty(startTime))
        {
        	 SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z",Locale.ENGLISH);
             startTimeDate = sf.parse(startTime);
             endTimeDate = sf.parse(endTime);
             startTimeString =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(startTimeDate);
             endTimeString =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(endTimeDate);
//        	startTimeDate = new Date(startTime);  
//        	
//        	endTimeDate = new Date(endTime);
        }
        Page<TransportOrderDto> page = new Page<>(dto.getStartPage(),dto.getPageSize());      
        List<TransportOrderDto> list = transportOrderService.findTransportOrderByPage(page,dto,createby,memberId,carrier,startTimeString,endTimeString);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list, page.getTotal(),page.getPages());
    }
    
    
    @RequestMapping(value = "/delTransportOrder" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delTransportOrder(ParamsDto dto,String[] transportOrderNos,String[] saleContractNos){
    	 if (null == dto.getIds() || dto.getIds().length == 0) {
             return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
         }
    	 transportOrderService.deleteBatchIds(Arrays.asList(dto.getIds()));
    	 List<String> saleDetailIdList = new ArrayList<String>();
    	 List<Stock> stockList = new ArrayList<Stock>();
    	 for(String sd :Arrays.asList(transportOrderNos))
    	 {
    		 TransportOrderDetail  transportOrderDetail = new TransportOrderDetail();
    		 transportOrderDetail.setTransportno(sd);
    		 EntityWrapper<TransportOrderDetail> eWrapper = new EntityWrapper<TransportOrderDetail>(transportOrderDetail);
    		 List<TransportOrderDetail> transportOrderDetailList = transportOrderDetailService.selectList(eWrapper);
    		 
    		 if(transportOrderDetailList.size()>0)
    		 {
    			 for (TransportOrderDetail pod:transportOrderDetailList)
    			 {
    				 saleDetailIdList.add(pod.getSaledetailid());
    			 }
    		 }
    		 
    	 }
    	 transportOrderDetailService.deleteBatchTransportOrderNos(Arrays.asList(transportOrderNos));
    	 saleContractDetailService.batchTransportOrderUpdate(Arrays.asList(saleContractNos),saleDetailIdList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", null);
    }
    
    
}
