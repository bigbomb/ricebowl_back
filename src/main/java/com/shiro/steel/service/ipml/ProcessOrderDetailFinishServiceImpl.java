package com.shiro.steel.service.ipml;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumStockStatus;
import com.shiro.steel.entity.ProcessOrder;
import com.shiro.steel.entity.ProcessOrderDetailFinish;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.mapper.ProcessOrderDetailFinishMapper;
import com.shiro.steel.mapper.ProcessOrderMapper;
import com.shiro.steel.mapper.SaleContractDetailMapper;
import com.shiro.steel.mapper.StockMapper;
import com.shiro.steel.pojo.vo.ProcessDetailFinishVo;
import com.shiro.steel.service.ProcessOrderDetailFinishService;

/** 

* @author 作者 lujunjie: 

* @version 创建时间：Dec 17, 2019 10:29:27 AM 

* 类说明 

*/
@Service
public class ProcessOrderDetailFinishServiceImpl extends ServiceImpl<ProcessOrderDetailFinishMapper, ProcessOrderDetailFinish> implements ProcessOrderDetailFinishService{

	@Autowired
	private StockMapper stockMapper;
	
	@Autowired
	private SaleContractDetailMapper saleContractDetailMapper;
	
	@Autowired
	private ProcessOrderMapper processOrderMapper;
	
	@Override
	public Boolean addProcessOrderFinish(ProcessDetailFinishVo processDetailFinishVo) {
		// TODO Auto-generated method stub
		try {
			String processDetailFinishDetail = processDetailFinishVo.getJgdetail();
			String delidString = processDetailFinishVo.getDelids();
			String processnoString = processDetailFinishVo.getProcessno();
	    	List<ProcessOrderDetailFinish>  processOrderDetailFinishList = JSONObject.parseArray(processDetailFinishDetail, ProcessOrderDetailFinish.class);
	    	List<Integer>  delIds = JSONObject.parseArray(delidString, Integer.class);
	    	List<ProcessOrderDetailFinish> noidprocessOrderDetailFinishList = new ArrayList<ProcessOrderDetailFinish>();
	    	List<ProcessOrderDetailFinish> idprocessOrderDetailFinishList = new ArrayList<ProcessOrderDetailFinish>();
	    	for(ProcessOrderDetailFinish podf:processOrderDetailFinishList)
	    	{
	    		podf.setProcessno(processnoString);
	    		if(podf.getId()==null)
	    		{
	    			podf.setFinalweight(podf.getActualweight());
	    			noidprocessOrderDetailFinishList.add(podf);
	    		}
	    		else {
					idprocessOrderDetailFinishList.add(podf);
				}
	    	}
	    	if(noidprocessOrderDetailFinishList.size()>0)
	    	{
	    		super.insertBatch(noidprocessOrderDetailFinishList);
	    		
	    	}
	    	if(idprocessOrderDetailFinishList.size()>0)
	    	{
	    		super.updateBatchById(idprocessOrderDetailFinishList);
	    	}
	    	if(delIds.size()>0)
	    	{
	    		super.baseMapper.deleteBatchIds(delIds);
	    	}
	    	EntityWrapper<ProcessOrder> powrapper = new EntityWrapper<ProcessOrder>();
	    	powrapper.eq("processNo", processnoString);
	    	ProcessOrder processOrder = new ProcessOrder();
	    	processOrder.setStatus(EnumStockStatus.PROCESSFINISH.getText());
	    	processOrderMapper.update(processOrder, powrapper);
	    	Stock stock = new Stock();
    		stock.setStatus(EnumStockStatus.PROCESSFINISH.getText());
    		stock.setId(processOrderDetailFinishList.get(0).getStockid());
    		stockMapper.updateById(stock);
    		EntityWrapper<SaleContractDetail> wrapper = new EntityWrapper<SaleContractDetail>();
    		wrapper.eq("stockid", processOrderDetailFinishList.get(0).getStockid());
    		SaleContractDetail saleContractDetail = new SaleContractDetail();
    		saleContractDetail.setProcessstatus(EnumStockStatus.PROCESSFINISH.getText());
    		saleContractDetailMapper.update(saleContractDetail, wrapper);
			return true;
		}catch(Exception e){
			return false;
		}
		
		
	}

	@Override
	public List<ProcessOrderDetailFinish> getProcessOrderFinish(String stockid) {
		// TODO Auto-generated method stub
		EntityWrapper<ProcessOrderDetailFinish> entityWrapper = new EntityWrapper<ProcessOrderDetailFinish>();
		entityWrapper.eq("stockid", stockid);
		return super.baseMapper.selectList(entityWrapper);
		
	}


}
