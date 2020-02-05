package com.shiro.steel.service.ipml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumStockStatus;
import com.shiro.steel.entity.DeliveryOrder;
import com.shiro.steel.entity.DeliveryOrderDetail;
import com.shiro.steel.entity.ProcessOrderDetailFinish;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.entity.WarehouseInfo;
import com.shiro.steel.exception.MyException;
import com.shiro.steel.mapper.DeliveryOrderMapper;
import com.shiro.steel.mapper.ProcessOrderDetailFinishMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.DeliveryOrderVo;
import com.shiro.steel.pojo.vo.SaleContractDetailVo;
import com.shiro.steel.pojo.vo.WarehouseInfoVo;
import com.shiro.steel.service.DeliveryOrderDetailService;
import com.shiro.steel.service.DeliveryOrderService;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.service.SaleContractService;
import com.shiro.steel.service.StockService;
import com.shiro.steel.service.WarehouseInfoService;
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
public class DeliveryOrderServiceImpl extends ServiceImpl<DeliveryOrderMapper, DeliveryOrder> implements DeliveryOrderService {
	final static String preName = "TD";
	@Autowired
	private WarehouseInfoService warehouseInfoService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private SaleContractService saleContractService;
	
	@Autowired
	private SaleContractDetailService  saleContractDetailService;
	
	@Autowired
	private DeliveryOrderDetailService deliveryOrderDetailService;
	
	@Autowired
	private DeliveryOrderMapper deliveryOrderMapper;

	@Autowired 
	private ProcessOrderDetailFinishMapper processOrderDetailFinishMapper;
	@Override
	public Boolean addDeliveryOrder(DeliveryOrderVo deliveryOrderVo) {
		// TODO Auto-generated method stub
		Boolean isstock = false;
		String deliveryno = "";
		StringBuffer selectedIdss = new StringBuffer();
		String saleContractDetail = deliveryOrderVo.getDeliveryOrderDetail();
		DeliveryOrder deliveryOrder = new DeliveryOrder();
 	    BeanCopier copier = BeanCopier.create(DeliveryOrderVo.class, DeliveryOrder.class, false);
 		copier.copy(deliveryOrderVo, deliveryOrder, null);
 	    deliveryOrder.setCrt(new Date());
 	    String warehouseId = deliveryOrderVo.getWarehouseid();
 	    UserInfoDto userInfoDto = new UserInfoDto();
 	    Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
 	    WarehouseInfo warehouseInfo = warehouseInfoService.selectById(warehouseId);
 	    deliveryno = preName+GeneratorUtil.getTimeStamp();
 	    deliveryOrder.setDeliveryno(deliveryno);
 	    deliveryOrder.setWarehouseid(warehouseId.toString());
 	    deliveryOrder.setWarehousename(warehouseInfo.getWarehousename());
 	    deliveryOrder.setWarehouseaddress(warehouseInfo.getWarehouseaddress());
 	    deliveryOrder.setWarehousephone(warehouseInfo.getWarehousephone());
 	    deliveryOrder.setWarehousefax(warehouseInfo.getWarehousefax());
 	    deliveryOrder.setCreateBy(userInfoDto.getUsername());
 	    super.baseMapper.insert(deliveryOrder);
 	    List<SaleContractDetailVo>  collection = JSONObject.parseArray(saleContractDetail, SaleContractDetailVo.class);
 	    BigDecimal totalAmount = new BigDecimal(0);
 	    BigDecimal totalWeight = new BigDecimal(0);
 	    List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
 	    List<Stock> stockList = new ArrayList<Stock>();
 	    for(SaleContractDetailVo s:collection){
 	      Stock stock = new Stock();	
 	      stock.setId(s.getStockid());
 	      if(EnumStockStatus.PROCESSFINISH.getText().equals(s.getProcessstatus()))
 	      {
 	    	 selectedIdss.append(s.getSelectedIdss()).append(",");
 	    	 stock.setStatus(EnumStockStatus.OUTSTOCKING.getText());
 	      }
 	      else {
 	    	 stock.setStatus(EnumStockStatus.LOCKSTOCK.getText());
		}
 	     
 		  BigDecimal amount = new BigDecimal(0);
  		  amount = s.getFinalweight().multiply(s.getPrice()).setScale(3,BigDecimal.ROUND_HALF_UP);
          //amount = s.getFinalweight().multiply(s.getPrice()).multiply(new BigDecimal(s.getNum())).setScale(3,BigDecimal.ROUND_HALF_UP);
  		  totalWeight = totalWeight.add(s.getFinalweight());
  		  totalAmount = totalAmount.add(amount);
  		  SaleContractDetail  newsaleContractDetail = new SaleContractDetail();
  		  newsaleContractDetail.setActualweight(s.getActualweight());
  		  newsaleContractDetail.setFinalweight(s.getFinalweight());
  		  newsaleContractDetail.setId(s.getId());
  		  newsaleContractDetail.setDeliverystatus(EnumStockStatus.OUTSTOCKING.getText());
  		  newsaleContractDetail.setStockid(s.getStockid());
  		  saleContractDetailList.add(newsaleContractDetail);
  		  stockList.add(stock);
  	   }
 	    List<DeliveryOrderDetail>  deliveryOrderDetailList = JSONObject.parseArray(saleContractDetail, DeliveryOrderDetail.class);
 	    for(DeliveryOrderDetail s:deliveryOrderDetailList){
// 	    	if(StringUtils.isNotBlank(s.getStockid().toString()))
// 	    	{
// 	    		isstock = true;
// 	    	}
 		   s.setDeliveryno(deliveryno);
 		   s.setCrt(new Date());
 		   if(s.getId()!=null)
 		   {
 	 		   s.setSaledetailid(s.getId().toString());
 		   }

 		   s.setId(null);
 	   }
  	    stockService.updateBatchById(stockList);
 	   
 	    saleContractDetailService.insertOrUpdateBatch(saleContractDetailList);
 	    SaleContract saleContract = new SaleContract();
 	    saleContract.setContractno(deliveryOrderVo.getContractno());
 	    saleContract.setActualamount(totalAmount);
 	    saleContract.setActualweight(totalWeight);
 	    saleContractService.updateByContract(saleContract);
 	   ProcessOrderDetailFinish processOrderDetailFinish = new ProcessOrderDetailFinish();
 	    EntityWrapper<ProcessOrderDetailFinish> processOrderDetailFinishEntityWrapper = new EntityWrapper<ProcessOrderDetailFinish>();
 	   processOrderDetailFinishEntityWrapper.in("id", selectedIdss.toString());
 	   processOrderDetailFinish.setDeliveryno(deliveryno);
 	   processOrderDetailFinish.setDeliverystatus(EnumStockStatus.OUTSTOCKING.getText());
 	   processOrderDetailFinishMapper.update(processOrderDetailFinish, processOrderDetailFinishEntityWrapper);
 	    
 	    Boolean status = deliveryOrderDetailService.insertBatch(deliveryOrderDetailList);
		return status;
	}

	@Override
	public List<DeliveryOrderVo> findByPage(Page<DeliveryOrderVo> page, ParamsDto dto, String memberId,String createby,String startTime,String endTime) {
		// TODO Auto-generated method stub
		List<DeliveryOrderVo> list = super.baseMapper.findByPage(page,dto,memberId,createby,startTime,endTime);
        return list;
	}

	@Override
	public List<DeliveryOrderDetail> findDetailByPageList( ParamsDto dto,
			String memberId, String deliveryNos) {
		// TODO Auto-generated method stub
		EntityWrapper<DeliveryOrderDetail> wrapper = new EntityWrapper<DeliveryOrderDetail>();
		
		wrapper.eq("deliveryNo", deliveryNos);
		List<DeliveryOrderDetail> list = deliveryOrderDetailService.selectList(wrapper);
		List<DeliveryOrderDetail> listcopy = new ArrayList<DeliveryOrderDetail>();

		for(DeliveryOrderDetail deliveryOrderDetail:list)
		{
			EntityWrapper<ProcessOrderDetailFinish> podfwrapper = new EntityWrapper<ProcessOrderDetailFinish>();
			listcopy.add(deliveryOrderDetail);
			podfwrapper.eq("deliveryNo",deliveryOrderDetail.getDeliveryno()).eq("stockId", deliveryOrderDetail.getStockid());
			List<ProcessOrderDetailFinish> pList = processOrderDetailFinishMapper.selectList(podfwrapper);
			if(pList.size()>0)
			{
				listcopy.remove(deliveryOrderDetail);
				for(ProcessOrderDetailFinish processOrderDetailFinish : pList)
				{
					DeliveryOrderDetail deliveryOrderDetailCopy = new DeliveryOrderDetail();
					BeanCopier copier = BeanCopier.create(ProcessOrderDetailFinish.class, DeliveryOrderDetail.class, false);
					copier.copy(processOrderDetailFinish, deliveryOrderDetailCopy, null);
					listcopy.add(deliveryOrderDetailCopy);
				}
			}
		}
		
//		List<DeliveryOrderDetailVo> list = deliveryOrderDetailService.findDetailByPageList(dto,memberId,deliveryNos);
        return listcopy;
	}

	@Override
	public Boolean delDeliveryOrder(ParamsDto dto, String[] deliveryOrderNos, String[] saleContractNos) throws MyException {
		// TODO Auto-generated method stub
		try {
		 super.baseMapper.deleteBatchIds(Arrays.asList(dto.getIds()));
    	 List<String> saleDetailIdList = new ArrayList<String>();
    	 List<String> stockIdList = new ArrayList<String>();
    	 List<Stock> stockList = new ArrayList<Stock>();
    	 for(String sd :Arrays.asList(deliveryOrderNos))
    	 {
    		 DeliveryOrderDetail  deliveryOrderDetail = new DeliveryOrderDetail();
    		 deliveryOrderDetail.setDeliveryno(sd);
    		 EntityWrapper<DeliveryOrderDetail> eWrapper = new EntityWrapper<DeliveryOrderDetail>(deliveryOrderDetail);
    		 List<DeliveryOrderDetail> deliveryOrderDetailList = deliveryOrderDetailService.selectList(eWrapper);
    		 
    		 if(deliveryOrderDetailList.size()>0)
    		 {
    			 for (DeliveryOrderDetail pod:deliveryOrderDetailList)
    			 {
    				 saleDetailIdList.add(pod.getSaledetailid());
    				 SaleContractDetail saleContractDetail =  saleContractDetailService.selectById(pod.getSaledetailid());
    				 if(EnumStockStatus.PROCESSFINISH.getText().equals(saleContractDetail.getProcessstatus()))
    				 {
    					 Stock stock = new Stock();
        				 stock.setId(pod.getStockid());
         				 stock.setStatus(EnumStockStatus.PROCESSFINISH.getText());
         				 stockList.add(stock);
    				 }
    				 stockIdList.add(pod.getStockid().toString());
//    				
    			 }
    		 }
    		 
    	 }
    	 if(stockIdList.size()>0)
    	 {
    		 stockService.updateBatchById(stockList);
    	 }
    	 deliveryOrderDetailService.deleteBatchDeliveryOrderNos(Arrays.asList(deliveryOrderNos));
    	 List<SaleContractDto> finaList = saleContractDetailService.selectByStockIdList(stockIdList);
    	 saleContractService.batchWeigtAmountUpdate(finaList);
    	 saleContractDetailService.batchDeliveryOrderUpdate(Arrays.asList(saleContractNos),saleDetailIdList);
    	 ProcessOrderDetailFinish processOrderDetailFinish = new ProcessOrderDetailFinish();
  	     EntityWrapper<ProcessOrderDetailFinish> processOrderDetailFinishEntityWrapper = new EntityWrapper<ProcessOrderDetailFinish>();
  	     processOrderDetailFinishEntityWrapper.in("deliveryNo", deliveryOrderNos);
  	     processOrderDetailFinish.setDeliverystatus("");
  	     processOrderDetailFinishMapper.update(processOrderDetailFinish, processOrderDetailFinishEntityWrapper);
    	 return true;
		}catch(MyException e){
			return false;
		}
	}



	@Override
	public Integer updateBatchByDeliveryOrder(List<DeliveryOrder> deliveryOrderList) {
		// TODO Auto-generated method stub
		return deliveryOrderMapper.updateBatchByDeliveryOrder(deliveryOrderList);
	}
	

    
}
