package com.shiro.steel.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.Enum.EnumStockStatus;
import com.shiro.steel.api.base.BaseApi;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.entity.WarehouseInfo;
import com.shiro.steel.exception.MyException;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.StockVo;
import com.shiro.steel.pojo.vo.WarehouseInfoVo;
import com.shiro.steel.service.ProcessOrderDetailService;
import com.shiro.steel.service.StockService;
import com.shiro.steel.service.WarehouseInfoService;
import com.shiro.steel.utils.RedisHelper;
import com.shiro.steel.utils.ResultUtil;

@RestController
@RequestMapping(value = "WareHouseApi/v1")
public class StockApi extends BaseApi{
	
	
	@Autowired
	private ProcessOrderDetailService  processOrderDetailService;
    
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private RedisHelper redisHelper;
	/**
     * @desc: 新增或更新仓库
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/saveWarehouse" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object saveWarehouse(WarehouseInfoVo warehouseInfoVo, BindingResult bindingResult) {   	
    	WarehouseInfo  warehouseInfo = new WarehouseInfo();
		BeanCopier copier = BeanCopier.create(WarehouseInfoVo.class, WarehouseInfo.class, false);
		copier.copy(warehouseInfoVo, warehouseInfo, null);
		if(warehouseInfo.getId()!=null)
		{
			warehouseInfo.setUpt(new Date());
		}
    	Boolean status = warehouseInfoService.insertOrUpdate(warehouseInfo);
    	if(status)
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    	}else
    	{
    		return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "保存失败");
    	}

    
    }
    
    @RequestMapping(value = "/findByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByPage(@ModelAttribute ParamsDto dto,String memberId){
        Page<WarehouseInfo> page = new Page<>(dto.getStartPage(),dto.getPageSize());
         List<WarehouseInfo> list = warehouseInfoService.findByPage(page, dto,memberId);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list, page.getTotal(),page.getPages());
    }
    
    @RequestMapping(value = "/findwarehouseByPo" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findwarehouseById(String warehouseId){
         WarehouseInfo warehouseInfo = warehouseInfoService.selectById(warehouseId);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", warehouseInfo);
    }
    
    
    @RequestMapping(value = "/findByNoPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByNoPage(@ModelAttribute ParamsDto dto,String memberId){
    	 WarehouseInfo warehouseInfo = new WarehouseInfo();
    	 warehouseInfo.setMemberid(memberId);
     	 EntityWrapper<WarehouseInfo> wrapper = new EntityWrapper<WarehouseInfo>(warehouseInfo);
         List<WarehouseInfo> list = warehouseInfoService.selectList(wrapper);
         return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list);
    }
    
    
    
    
    @RequestMapping(value = "/findByPageList" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByPageList(String processNo){
    	ProcessOrderDetail processOrderDetail = new ProcessOrderDetail();
    	processOrderDetail.setProcessno(processNo);
     	 EntityWrapper<ProcessOrderDetail> wrapper = new EntityWrapper<ProcessOrderDetail>(processOrderDetail);
         List<ProcessOrderDetail> list = processOrderDetailService.selectList(wrapper);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list);
    }
    
    @RequestMapping(value = "/delWarehouseRow" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delWarehouseRow(Integer id){
         Boolean status = warehouseInfoService.deleteById(id);
         if(status)
     	{
     		return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
     	}else
     	{
     		return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "删除失败");
     	}
    }
    
    @RequestMapping(value = "/findItemByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findItemByPage(@ModelAttribute ParamsDto dto,@ModelAttribute StockVo stockVo,String stockstatus,String startTime,String endTime) throws ParseException {
    	 Page<Stock> page = new Page<>(dto.getStartPage(),dto.getPageSize());
    	 BeanCopier copier = BeanCopier.create(StockVo.class, Stock.class, false);
    	 Stock stock = new Stock();
    	 copier.copy(stockVo, stock, null);
		 Date startTimeDate = null;
		 Date endTimeDate = null;
		 String startTimeString = "";
		 String endTimeString = "";

     	 EntityWrapper<Stock> wrapper = new EntityWrapper<Stock>(stock);
     	 wrapper.gt("num", 0);
     	 if("jg".equals(stockstatus))
     	 {
     		wrapper.eq("status", EnumStockStatus.LOCKSTOCK.getText());
     	 }else if("td".equals(stockstatus))
     	 {
         	 wrapper.eq("status",EnumStockStatus.LOCKSTOCK.getText()).or().eq("status",EnumStockStatus.PROCESSFINISH.getText());
     	 }else {
     		 wrapper.eq("status",stockstatus);
		}
		if(!StringUtils.isEmpty(startTime))
		{
			SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);
			startTimeDate = sf.parse(startTime);
			endTimeDate = sf.parse(endTime);
			startTimeString =new SimpleDateFormat("yyyy-MM-dd").format(startTimeDate);
			endTimeString =new SimpleDateFormat("yyyy-MM-dd").format(endTimeDate);
			wrapper.ge("crt",startTimeString).and().le("crt",endTimeString);
		}
		wrapper.orderBy("crt",false);
         Page<Stock> list = stockService.selectPage(page,wrapper);
         return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list.getRecords(), page.getTotal(),page.getPages());
    }
    
    @RequestMapping(value = "/findItemByCustomerId" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findItemByCustomerId(@ModelAttribute StockVo stockVo,String stockstatus){
    	 BeanCopier copier = BeanCopier.create(StockVo.class, Stock.class, false);
    	 Stock stock = new Stock();
    	copier.copy(stockVo, stock, null);
     	 EntityWrapper<Stock> wrapper = new EntityWrapper<Stock>(stock);
     	 wrapper.gt("num", 0);
     	  wrapper.eq("status", EnumStockStatus.LOCKSTOCK.getText()).and().eq("customerId", stock.getCustomerid());
     	 

         List<Stock> list = stockService.selectList(wrapper);
         return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list);
    }
    
    
    
    @RequestMapping(value = "/lock" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object lock(String ids,String nums,String customerId,String customerName,String productids){
    	try {
    		Boolean result = stockService.lock(ids,nums,customerId,customerName,productids);
    		return ResultUtil.result(EnumCode.OK.getValue(),"锁货成功"); 
    	}catch(MyException e)
    	{
    		return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(),"锁货失败"); 
    	}
    	
    	
    }
    
    @RequestMapping(value = "/unlock" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object unlock(String ids,String productids,String nums){
    	try
    	{
    		stockService.batchUpdateBykey(ids, productids, nums);
    		return ResultUtil.result(EnumCode.OK.getValue(),"解锁成功"); 
    	}catch(MyException e)
    	{
    		return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(),"解锁失败"); 
    	}
    	
    }
    
   
}
