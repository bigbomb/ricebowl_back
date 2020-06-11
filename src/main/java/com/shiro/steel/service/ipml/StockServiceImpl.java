package com.shiro.steel.service.ipml;

import java.math.BigDecimal;
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

	ThreadLocal<Map<String,Integer>> localnum = new ThreadLocal<Map<String, Integer>>();
	ThreadLocal<Map<String,BigDecimal>> localweight = new ThreadLocal<Map<String, BigDecimal>>();
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
	public Integer batchUpdateBykey(String ids,String productids,String nums,String weights) throws MyException{
		// TODO Auto-generated method stub
		List<Stock> updatestocklist = new ArrayList<Stock>();
    	List<Stock> addstocklist = new ArrayList<Stock>();
    	UserInfoDto userInfoDto = new UserInfoDto();
        Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
    	String[] st = ids.split(",");
    	String[] num = nums.split(",");
    	String[] weight = weights.split(",");
    	String[] pd = productids.split(",");
    	Map<String,Integer> numMap = new HashMap<String,Integer>();
        Map<String,BigDecimal> weightMap = new HashMap<>();
    	 try {
	    		 int i = 0;
	    		 int sumNum=0;
	    		 BigDecimal sumWeight = new BigDecimal(0);
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
//	    			    	if(stock.getNum()==0 ||)
//	    			    	{
//	    			    		stockMapper.deleteById(Integer.valueOf(id));
//	    			    		stock = new Stock();
//	    			    		if(numMap.containsKey(pd[i])&&weightMap.containsKey(pd[i]))
//				    			{
//				    				sumNum = Integer.valueOf(numMap.get(pd[i]))+Integer.valueOf(num[i]);
//				    				numMap.put(pd[i], sumNum);
//				    				sumWeight = weightMap.get(pd[i]).add(new BigDecimal(weight[i])).setScale(5,BigDecimal.ROUND_HALF_UP);
//				    				weightMap.put(pd[i],sumWeight);
//				    			}
//				    			else
//				    			{
//				    				numMap.put(pd[i], Integer.valueOf(num[i]));
//				    				weightMap.put(pd[i],new BigDecimal(weight[i]).setScale(5,BigDecimal.ROUND_HALF_UP));
//				    			}
//
//				    	    	localnum.set(numMap);
//								localweight.set(weightMap);
//		        	    		stock.setNum(localnum.get().get(pd[i]));
//		        	    		stock.setWeight(localweight.get().get(pd[i]));
//	    			    		stock.setProductid(pd[i]);
//	    			    		stock.setId(Integer.valueOf(id));
//		    			    	stock.setStatus(EnumStockStatus.INSTOCK.getText());
//	    			    		stock.setLockman("");
//	    			    		updatestocklist.add(stock);
//	    			    	}
//				    		//如果判断只是锁了一部分
//				    		else {
				    			stockMapper.deleteById(Integer.valueOf(id));
				    			if(numMap.containsKey(pd[i])&&weightMap.containsKey(pd[i]))
				    			{
				    				sumNum = Integer.valueOf(numMap.get(pd[i]))+Integer.valueOf(num[i]);
				    				numMap.put(pd[i], sumNum);
									sumWeight = weightMap.get(pd[i]).add(new BigDecimal(weight[i])).setScale(5,BigDecimal.ROUND_HALF_UP);
									weightMap.put(pd[i],sumWeight);
				    			}
				    			else
				    			{
				    				numMap.put(pd[i], Integer.valueOf(num[i]));
									weightMap.put(pd[i],new BigDecimal(weight[i]).setScale(5,BigDecimal.ROUND_HALF_UP));
				    			}
				    			
				    	    	localnum.set(numMap);
				    			localweight.set(weightMap);
		        	    		stock.setNum(localnum.get().get(pd[i])+stock.getNum());
								stock.setWeight(localweight.get().get(pd[i]).add(stock.getWeight()));
								stock.setOriweight(localweight.get().get(pd[i]).add(stock.getWeight()));
		        	    		stock.setProductid(pd[i]);
		        	    		stock.setStatus(EnumStockStatus.INSTOCK.getText());
		        	    		addstocklist.add(stock);
//				    		}
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
    		   localweight.remove();
    	   }
		return 1;
    }
	@Override
	public Boolean lock(String ids, String nums,String weights,String customerId,String customerName, String productids) throws MyException{
		// TODO Auto-generated method stub
		List<Stock> successstocklist = new ArrayList<Stock>();
    	List<Stock> failstocklist = new ArrayList<Stock>();
    	UserInfoDto userInfoDto = new UserInfoDto();
        Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
    	String[] st = ids.split(",");
    	String[] num = nums.split(",");
    	String[] weight = weights.split(",");
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
    	        	    		stock.setNum(stock.getNum()-Integer.valueOf(num[i]));
    	        	    		stock.setWeight(stock.getWeight().subtract(new BigDecimal(weight[i])).setScale(5,BigDecimal.ROUND_HALF_UP));
    	        	    		newwrapper.eq("id", id);
    	        	    		super.baseMapper.update(stock, newwrapper);
    	        	    		stock.setStatus(EnumStockStatus.LOCKSTOCK.getText());
    	        	    		stock.setLockman(userInfoDto.getUsername());
							    stock.setNum(Integer.valueOf(num[i]));
							    stock.setWeight(new BigDecimal(weight[i]).setScale(5,BigDecimal.ROUND_HALF_UP));
    	        	    		stock.setCustomerid(customerId);
    	        	    		stock.setCustomername(customerName);
                                stock.setParentstockid(Integer.valueOf(id));
                                stock.setOriweight(new BigDecimal(0));
                                stock.setOrinum(0);
                                EntityWrapper<Stock> stockwrapper = new EntityWrapper<Stock>();
                                stockwrapper.eq("parentStockId",id).eq("customerId",customerId).eq("status",EnumStockStatus.LOCKSTOCK.getText());
                                List<Stock> newstockList = super.baseMapper.selectList(stockwrapper);
                                if(newstockList.size()==1)
								{
									stock.setNum(newstockList.get(0).getNum()+Integer.valueOf(num[i]));
									stock.setWeight(newstockList.get(0).getWeight().add(new BigDecimal(weight[i]).setScale(5,BigDecimal.ROUND_HALF_UP)));
									status = true;
								}
                                else
								{

									super.baseMapper.insert(stock);


        			    		}
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
                super.baseMapper.batchAddByPIdCusId(successstocklist);
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

