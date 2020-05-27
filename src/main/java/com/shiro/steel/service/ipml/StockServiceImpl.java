package com.shiro.steel.service.ipml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumStockStatus;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.exception.MyException;
import com.shiro.steel.mapper.StockMapper;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.service.StockService;
import com.shiro.steel.utils.RedisHelper;

@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService{

	@Autowired
	private StockMapper stockMapper;
	
	 @Autowired
	    private RedisHelper redisHelper;
	@Override
	public Integer updateByPrimaryKey(Stock stock) {
		// TODO Auto-generated method stub
		return stockMapper.updateByPrimaryKey(stock);
	}
	@Override
	public Integer batchUpdateBykey(String ids,String productids,String nums) throws MyException{
		// TODO Auto-generated method stub
		ThreadLocal<Map<String,Integer>> localnum = new ThreadLocal<Map<String, Integer>>();
		List<Stock> updatestocklist = new ArrayList<Stock>();
    	List<Stock> addstocklist = new ArrayList<Stock>();
    	UserInfoDto userInfoDto = new UserInfoDto();
        Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
    	String[] st = ids.split(",");
    	String[] num = nums.split(",");
    	String[] pd = productids.split(",");
    	Map<String,Integer> numMap = new HashMap<String,Integer>();

    	 try {
	    		 int i = 0;
	    		 int sumNum=0;
	    		 for (String id : st) 
	    		 {  
	    			    Boolean lckBoolean = redisHelper.lock(id);
	    			    if(lckBoolean)
	    			    {
	    			    	Stock stock  = new Stock();
	    			    	stock.setProductid(pd[i]);
	    			    	stock.setStatus(EnumStockStatus.INSTOCK.getText());
	    			    	stock = stockMapper.selectOne(stock);
	    			    	//如果锁货全部锁完
	    			    	if(stock.getNum()==0)
	    			    	{
	    			    		stockMapper.deleteById(Integer.valueOf(id));
	    			    		stock = new Stock();
	    			    		if(numMap.containsKey(pd[i]))
				    			{
				    				sumNum = Integer.valueOf(numMap.get(pd[i]))+Integer.valueOf(num[i]);
				    				numMap.put(pd[i], sumNum);
				    			}
				    			else
				    			{
				    				numMap.put(pd[i], Integer.valueOf(num[i]));
				    			}
				    			
				    	    	localnum.set(numMap);
		        	    		stock.setNum(localnum.get().get(pd[i]));
	    			    		stock.setProductid(pd[i]);
		    			    	stock.setStatus(EnumStockStatus.INSTOCK.getText());
	    			    		stock.setLockman("");
	    			    		updatestocklist.add(stock);
	    			    	}
				    		//如果判断只是锁了一部分
				    		else {
				    			stockMapper.deleteById(Integer.valueOf(id));
				    			if(numMap.containsKey(pd[i]))
				    			{
				    				sumNum = Integer.valueOf(numMap.get(pd[i]))+Integer.valueOf(num[i]);
				    				numMap.put(pd[i], sumNum);
				    			}
				    			else
				    			{
				    				numMap.put(pd[i], Integer.valueOf(num[i]));
				    			}
				    			
				    	    	localnum.set(numMap);
		        	    		stock.setNum(localnum.get().get(pd[i])+stock.getNum());
		        	    		stock.setProductid(pd[i]);
		        	    		stock.setStatus(EnumStockStatus.INSTOCK.getText());
		        	    		addstocklist.add(stock);
				    		}
	    			    }
	        	        i++; 	    		
	    		}
	    		 if(updatestocklist.size()>0)
	    		 {
		    		 stockMapper.batchUpdateBykeys(updatestocklist);
	    		 }
	    		 if(addstocklist.size()>0)
	    		 {
	    			 stockMapper.batchAddBykeys(addstocklist);
	    		 }
	    		
    	    }catch(MyException e)
		    	 {
		    	    	throw new MyException(e);
		    	 }
    	   finally {
    		   localnum.remove();
    	   }
		return 1;
    }
	@Override
	public Boolean lock(String ids, String nums,String customerId,String customerName, String productids) throws MyException{
		// TODO Auto-generated method stub
		List<Stock> successstocklist = new ArrayList<Stock>();
    	List<Stock> failstocklist = new ArrayList<Stock>();
    	UserInfoDto userInfoDto = new UserInfoDto();
        Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
    	String[] st = ids.split(",");
    	String[] num = nums.split(",");
//    	String[] pd = productids.split(",");
    	 try {
    		 int i = 0;
    		 for (String id : st) {  
    			    Boolean lckBoolean = redisHelper.lock(id);
    			    if(lckBoolean)
    			    {
    			    	Stock stock  = new Stock();
    			    	stock.setId(Integer.valueOf(id));
    			    	stock.setStatus(EnumStockStatus.INSTOCK.getText());
    			    	stock = super.baseMapper.selectOne(stock);
    			    	Boolean status = false;
    			    	if(stock!=null)
    			    	{
    			    		EntityWrapper<Stock> newwrapper = new EntityWrapper<Stock>();
//    			    		//如果判断是锁货的数量和库存数一致
//    			    		if(stock.getNum()==Integer.valueOf(num[i]))
//    			    		{
//    	        	    		stock.setStatus(EnumStockStatus.LOCKSTOCK.getText());
//    	        	    		stock.setLockman(userInfoDto.getUsername());
//    	        	    		newwrapper.eq("productId", id);
//    	        	    		status=stockService.update(stock, newwrapper);
//    			    		}
//    			    		//如果判断锁货的数量小于库存数
//    			    		else {
    			    		    
    	        	    		stock.setNum(stock.getNum()-Integer.valueOf(num[i]));
    	        	    		newwrapper.eq("id", id);
    	        	    		super.baseMapper.update(stock, newwrapper);
    	        	    		stock.setStatus(EnumStockStatus.LOCKSTOCK.getText());
    	        	    		stock.setLockman(userInfoDto.getUsername());
    	        	    		stock.setNum(Integer.valueOf(num[i]));
    	        	    		stock.setCustomerid(customerId);
    	        	    		stock.setCustomername(customerName);
    	        	    		super.baseMapper.insert(stock);
//    			    		}
    			    	}
        	    		
        	    		if(status)
        	    		{
        	    			successstocklist.add(stock);
        	    		}
        	    		else
        	    		{
        	    			failstocklist.add(stock);
        	    		}
        	    		i++	;
        	    		
    			    }
    	    }
    		if (successstocklist.size()>0)
    		{
    			return true;  
    		}
    		else {
    			return false;
    		}
    	 
		} catch (MyException e) {
			// TODO: handle exception
			return false;
		}   
	}

	@Override
	public Boolean batchUpdatebyPurId(List<Stock> stockList) {
		Boolean st =super.baseMapper.batchUpdatebyPurId(stockList);
		return st;
	}


}

