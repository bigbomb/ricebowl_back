package com.shiro.steel.api;

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
import org.springframework.cglib.beans.BeanCopier;
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
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.entity.DeliveryOrder;
import com.shiro.steel.entity.DeliveryOrderDetail;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.entity.WarehouseInfo;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.ContractVo;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;
import com.shiro.steel.pojo.vo.DeliveryOrderVo;
import com.shiro.steel.pojo.vo.ProcessOrderVo;
import com.shiro.steel.service.DeliveryOrderDetailService;
import com.shiro.steel.service.DeliveryOrderService;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.service.StockService;
import com.shiro.steel.service.WarehouseInfoService;
import com.shiro.steel.utils.ResultUtil;

@RestController
@RequestMapping(value = "DeliveryOrderApi/v1")
public class DeliveryOrderApi extends BaseApi{
	
	@Autowired 
	private DeliveryOrderService deliveryOrderService ;
	
	@Autowired 
	private DeliveryOrderDetailService deliveryOrderDetailService ;
	
	@Autowired
	private SaleContractDetailService saleContractDetailService;
	
	@Autowired
	private StockService stockService;
	/**
     * @desc: 新增报价单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/addDeliveryOrder" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addDeliveryOrder(@Validated DeliveryOrderVo deliveryOrderVo,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResultUtil.result(EnumCode.OK.getValue(), errorList.toString());
        	}
    	    Boolean status =  deliveryOrderService.addDeliveryOrder(deliveryOrderVo);
    	   
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
    public Object findDetailByPageList(ParamsDto dto,String memberId,String deliveryNo){
//        Page<DeliveryOrderDetailVo> page = new Page<>(dto.getStartPage(),dto.getPageSize());
         List<DeliveryOrderDetailVo> list = deliveryOrderService.findDetailByPageList(dto,memberId,deliveryNo);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list);
    }
    
    
    @RequestMapping(value = "/findByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByPage(ParamsDto dto,String memberId,String startTime,String endTime) throws ParseException{
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
        Page<DeliveryOrderVo> page = new Page<>(dto.getStartPage(),dto.getPageSize());
         List<DeliveryOrderVo> list = deliveryOrderService.findByPage(page, dto,memberId,createby,startTimeString,endTimeString);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list, page.getTotal(),page.getPages());
    }
    
    
    
    @RequestMapping(value = "/delDeliveryOrder" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delDeliveryOrder(ParamsDto dto,String[] deliveryOrderNos,String[] saleContractNos){
    	 if (null == dto.getIds() || dto.getIds().length == 0) {
             return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
         }
    	 deliveryOrderService.deleteBatchIds(Arrays.asList(dto.getIds()));
    	 List<String> saleDetailIdList = new ArrayList<String>();
    	 List<Stock> stockList = new ArrayList<Stock>();
    	 for(String sd :Arrays.asList(deliveryOrderNos))
    	 {
    		 DeliveryOrderDetail  deliveryOrderDetail = new DeliveryOrderDetail();
    		 deliveryOrderDetail.setDeliveryno(sd);
    		 EntityWrapper<DeliveryOrderDetail> eWrapper = new EntityWrapper<DeliveryOrderDetail>(deliveryOrderDetail);
    		 List<DeliveryOrderDetail> deliveryOrderDetailList = deliveryOrderDetailService.selectList(eWrapper);
    		 
//    		 if(deliveryOrderDetailList.size()>0)
//    		 {
//    			 for (DeliveryOrderDetail pod:deliveryOrderDetailList)
//    			 {
//    				 saleDetailIdList.add(pod.getSaledetailid());
//    				 Stock stock = new Stock();
//    				 stock.setId(pod.getStockid());
//    				 stock.setStatus("在库");
//    				 stockList.add(stock);
//    			 }
//    		 }
    		 
    	 }
    	 stockService.updateBatchById(stockList);
    	 deliveryOrderDetailService.deleteBatchDeliveryOrderNos(Arrays.asList(deliveryOrderNos));
    	 saleContractDetailService.batchDeliveryOrderUpdate(Arrays.asList(saleContractNos),saleDetailIdList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", null);
    }
    
}
