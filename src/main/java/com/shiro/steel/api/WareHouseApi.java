package com.shiro.steel.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
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
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.WarehouseInfoVo;
import com.shiro.steel.service.ProcessOrderDetailService;
import com.shiro.steel.service.StockService;
import com.shiro.steel.service.WarehouseInfoService;
import com.shiro.steel.utils.RedisHelper;
import com.shiro.steel.utils.ResultUtil;

@RestController
@RequestMapping(value = "WareHouseApi/v1")
public class WareHouseApi extends BaseApi{
	
	
	@Autowired
	private ProcessOrderDetailService  processOrderDetailService;
    
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private RedisHelper redisHelper;
	/**
     * @desc: 新增报价单
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
    	Boolean status = warehouseInfoService.insertOrUpdate(warehouseInfo);
    	if(status)
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    	}else
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "保存失败");
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
     		return ResultUtil.result(EnumCode.OK.getValue(), "删除失败");
     	}
    }
    
    @RequestMapping(value = "/findItemByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findItemByPage(@ModelAttribute ParamsDto dto,@ModelAttribute Stock stock,String stockstatus){
    	 Page<Stock> page = new Page<>(dto.getStartPage(),dto.getPageSize());
    	 
    	 stock.setStatus(stockstatus);
     	 EntityWrapper<Stock> wrapper = new EntityWrapper<Stock>(stock);
         Page<Stock> list = stockService.selectPage(page,wrapper);
         return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list.getRecords(), page.getTotal(),page.getPages());
    }
    
    @RequestMapping(value = "/lock" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object lock(String ids,String endTime){
    	List<Stock> stocklist = new ArrayList<Stock>();
    	 String[] st = ids.split(",");  
    	 try {
    		 for (String id : st) {  
    			    Boolean lckBoolean = redisHelper.lock(id);
    			    if(lckBoolean)
    			    {
    			    	Stock stock  = new Stock();
        	    		stock.setId(Integer.valueOf(id));
        	    		stock.setStatus(EnumStockStatus.LOCKSTOCK.getText());
        	    		stocklist.add(stock);
    			    }
    	    }
    		Boolean status =stockService.updateBatchById(stocklist);
    		if (status)
    		{
    			StringBuffer stocklockBuffer = new StringBuffer();
    			stocklist.forEach(str-> stocklockBuffer.append(str.getId()+","));
    			return ResultUtil.result(EnumCode.OK.getValue(),stocklockBuffer.toString()+ "锁货成功");  
    		}
    		else {
    			return ResultUtil.result(EnumCode.OK.getValue(), stocklist.toString() +"锁货失败");  
    		}
    	 
		} catch (Exception e) {
			// TODO: handle exception
			return ResultUtil.result(EnumCode.OK.getValue(), "锁货失败");  
		}
    	 
		
         
    }
    
   
}
