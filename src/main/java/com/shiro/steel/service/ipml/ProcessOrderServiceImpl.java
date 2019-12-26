package com.shiro.steel.service.ipml;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumStockStatus;
import com.shiro.steel.entity.ProcessOrder;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.entity.ProcessOrderDetailFinish;
import com.shiro.steel.entity.ProcessTemplate;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.mapper.ProcessOrderMapper;
import com.shiro.steel.mapper.ProcessTemplateMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.ProcessDetailFinishVo;
import com.shiro.steel.pojo.vo.ProcessOrderVo;
import com.shiro.steel.service.ProcessOrderDetailFinishService;
import com.shiro.steel.service.ProcessOrderDetailService;
import com.shiro.steel.service.ProcessOrderService;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.service.StockService;
import com.shiro.steel.utils.GeneratorUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
@Service
public class ProcessOrderServiceImpl extends ServiceImpl<ProcessOrderMapper, ProcessOrder> implements ProcessOrderService {
	final static String preName = "JG";
	@Autowired
	private ProcessOrderDetailService processOrderDetailService;
	@Autowired
	private SaleContractDetailService saleContractDetailService;
	@Autowired
	private ProcessTemplateMapper processTemplateMapper;
    @Autowired
    private StockService stockService;
    @Autowired
    private ProcessOrderDetailFinishService processOrderDetailFinishService;
	@Override
	public Boolean addProcessOrder(ProcessOrderVo processOrderVo,Integer contractId) {
		// TODO Auto-generated method stub
		String processNo = "";
		List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
		ProcessOrder processOrder = new ProcessOrder();		
		BeanCopier copier = BeanCopier.create(ProcessOrderVo.class, ProcessOrder.class, false);		
		copier.copy(processOrderVo, processOrder, null);		
		processNo = preName+GeneratorUtil.getTimeStamp();
		processOrder.setProcessno(processNo);
		processOrder.setCrt(new Date());
		UserInfoDto userInfoDto = new UserInfoDto();
 	    Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
	    processOrder.setCreateBy(userInfoDto.getUsername());
 	   
 	    //对加工模板进行操作
 	    ProcessTemplate processTemplate = new ProcessTemplate();
 	    processTemplate.setCustomerid(processOrderVo.getCustomerId());
 	    processTemplate.setMemberid(processOrderVo.getMemberId());
 	    processTemplate.setRemark(processOrderVo.getRemark());
 	    processTemplate.setWarehouseid(processOrderVo.getWarehouseId());
 	    processTemplate.setProcessfee(processOrderVo.getProcessfee());
 	    processTemplate.setProcesstype(processOrderVo.getProcesstype());
 	    processTemplate.setWarehousename(processOrderVo.getWarehouseName());
 	    processTemplate.setRemark(processOrderVo.getRemark());
 	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 	    processTemplate.setTemplatename(processOrderVo.getCustomerName()+simpleDateFormat.format(new Date()));
 	    if(StringUtils.isEmpty(processOrderVo.getTemplateId()))
 	    {
 	    	processTemplate.setCrt(new Date());
 	    	processTemplate.setCreateby(userInfoDto.getUsername());
 	 	    processTemplateMapper.insert(processTemplate);
 	    }
 	    else
 	    {
 	    	processTemplate.setId(Integer.valueOf(processOrderVo.getTemplateId()));
 	    	processTemplateMapper.updateById(processTemplate);
 	    }
 	    String processOrderDetail = processOrderVo.getProcessOrderDetail();
    	List<ProcessOrderDetail>  collection = JSONObject.parseArray(processOrderDetail, ProcessOrderDetail.class);
    	BigDecimal processweight = new BigDecimal(0);
    	List<Integer> stockidList = new ArrayList<>();
    	for(ProcessOrderDetail s:collection){
    		   processweight = processweight.add(s.getActualweight());
	  		   s.setProcessno(processNo);
	  		   s.setSaleDetailId(s.getId().toString());
	  		   s.setCrt(new Date());
	  		   SaleContractDetail scd = new SaleContractDetail();
	  		   scd.setId(s.getId());
	  		   scd.setStockid(s.getStockid());
	  		   stockidList.add(s.getStockid());
	  		   scd.setActualweight(s.getActualweight());
	  		   scd.setWarehousename(s.getWarehousename());
	  		   scd.setProcessstatus(EnumStockStatus.PROCESS.getText());
	  		   scd.setRemark(s.getRemark());
	  		   saleContractDetailList.add(scd);
	  	   }
    	 processOrder.setProcessweight(processweight);
    	 super.baseMapper.insert(processOrder);
    	 Stock stock = new Stock();
    	 stock.setStatus(EnumStockStatus.PROCESS.getText());
    	 EntityWrapper<Stock> stockWrapper = new EntityWrapper<Stock>();
    	 stockWrapper.in("id", stockidList);
    	 stockService.update(stock, stockWrapper);
    	 processOrderDetailService.insertBatch(collection);
//    	 SaleContract saleContract = new SaleContract();
//    	 saleContract.setId(contractId);
//    	 saleContract.setContractstatus("已审核");
//    	 saleContractService.updateById(saleContract);
    	 
    	 Boolean status = saleContractDetailService.updateBatchById(saleContractDetailList);
    	return status;
	}
	@Override
	public List<ProcessOrderVo> findByPage(Page<ProcessOrderVo> page, ParamsDto dto,String memberId,String createby,String startTime,String endTime) {
		// TODO Auto-generated method stub
		List<ProcessOrderVo> list = super.baseMapper.findByPage(page,dto,memberId,createby,startTime,endTime);
        return list;
	}
	@Override
	public Boolean delProcessOrder(ParamsDto dto, String[] processNos, String[] saleContractNos) {
		// TODO Auto-generated method stub
		try {
			 super.baseMapper.deleteBatchIds(Arrays.asList(dto.getIds()));
		   	 List<String> saleDetailIdList = new ArrayList<String>();
		   	 List<Integer> stockidList = new ArrayList<Integer>();
		   	 for(String sd :Arrays.asList(processNos))
		   	 {
		   		 ProcessOrderDetail  processOrderDetail = new ProcessOrderDetail();
		   		 processOrderDetail.setProcessno(sd);
		   		 EntityWrapper<ProcessOrderDetail> eWrapper = new EntityWrapper<ProcessOrderDetail>(processOrderDetail);
		   		 List<ProcessOrderDetail> processOrderDetailList= processOrderDetailService.selectList(eWrapper);
		   		 if(processOrderDetailList.size()>0)
		   		 {
		   			 for (ProcessOrderDetail pod:processOrderDetailList)
		   			 {
		   				 saleDetailIdList.add(pod.getSaleDetailId());
		   				 stockidList.add(pod.getStockid());
		   			 }
		   		 }
		   		 
		   	 }
	   	 Stock stock = new Stock();
    	 stock.setStatus(EnumStockStatus.LOCKSTOCK.getText());
    	 EntityWrapper<Stock> stockWrapper = new EntityWrapper<Stock>();
    	 stockWrapper.in("id", stockidList);
    	 stockService.update(stock, stockWrapper);
	   	 processOrderDetailService.deleteBatchProcessNos(Arrays.asList(processNos));
	   	 EntityWrapper<ProcessOrderDetailFinish> podf = new EntityWrapper<ProcessOrderDetailFinish>();
	   	 podf.in("processNo", Arrays.asList(processNos));
	   	 processOrderDetailFinishService.delete(podf);
	   	 saleContractDetailService.batchProcessUpdate(Arrays.asList(saleContractNos),saleDetailIdList);
	   	 return true;
		}catch(Exception e)
		{
			return false;
		}
		
	}
	

    
}
