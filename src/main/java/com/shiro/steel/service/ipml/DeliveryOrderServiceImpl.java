package com.shiro.steel.service.ipml;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.shiro.steel.Enum.EnumLiftingFee;
import com.shiro.steel.Enum.EnumTransportFee;
import com.shiro.steel.pojo.dto.*;
import com.shiro.steel.utils.CollectorsUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.Enum.EnumStockStatus;
import com.shiro.steel.entity.DeliveryOrder;
import com.shiro.steel.entity.DeliveryOrderDetail;
import com.shiro.steel.entity.ProcessOrderDetailFinish;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.entity.TransportOrder;
import com.shiro.steel.entity.WarehouseInfo;
import com.shiro.steel.exception.MyException;
import com.shiro.steel.mapper.DeliveryOrderMapper;
import com.shiro.steel.mapper.ProcessOrderDetailFinishMapper;
import com.shiro.steel.mapper.TransportOrderMapper;
import com.shiro.steel.pojo.vo.DeliveryOrderVo;
import com.shiro.steel.pojo.vo.SaleContractDetailVo;
import com.shiro.steel.service.DeliveryOrderDetailService;
import com.shiro.steel.service.DeliveryOrderService;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.service.SaleContractService;
import com.shiro.steel.service.StockService;
import com.shiro.steel.service.WarehouseInfoService;
import com.shiro.steel.utils.CommonUtil;
import com.shiro.steel.utils.ResultUtil;

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
	
	@Autowired
	private TransportOrderMapper transportOrderMapper;
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
 	    deliveryno = preName+CommonUtil.getTimeStamp();
 	    deliveryOrder.setDeliveryno(deliveryno);
 	    deliveryOrder.setWarehouseid(warehouseId.toString());
 	    deliveryOrder.setWarehousename(warehouseInfo.getWarehousename());
 	    deliveryOrder.setWarehouseaddress(warehouseInfo.getWarehouseaddress());
 	    deliveryOrder.setWarehousephone(warehouseInfo.getWarehousephone());
 	    deliveryOrder.setWarehousefax(warehouseInfo.getWarehousefax());
 	    deliveryOrder.setCreateBy(userInfoDto.getUsername());
 	    deliveryOrder.setStatus(EnumStockStatus.OUTSTOCKING.getText());
		if(EnumLiftingFee.TAXFREE.getValue()==(deliveryOrderVo.getFeeoption())|| EnumTransportFee.TAXINCLUDED.getValue()==(deliveryOrderVo.getFeeoption()) )
		{

			deliveryOrder.setLiftingfee(deliveryOrderVo.getLiftingfee().multiply(deliveryOrderVo.getWeight()));
			deliveryOrder.setLiftingperfee(Integer.valueOf(deliveryOrderVo.getLiftingfee().toString()));

		}
		else
		{
			deliveryOrder.setLiftingfee(deliveryOrderVo.getLiftingfee());
		}
 	    super.baseMapper.insert(deliveryOrder);
 	    List<SaleContractDetailVo>  collection = JSONObject.parseArray(saleContractDetail, SaleContractDetailVo.class);
 	    BigDecimal totalAmount = new BigDecimal(0);
 	    BigDecimal totalWeight = new BigDecimal(0);
 	    List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
 	    List<Stock> stockList = new ArrayList<Stock>();
 	    Map<Integer,BigDecimal> actualWeightMap = new HashMap<Integer,BigDecimal>();
		Map<Integer,BigDecimal> finalWeightMap = new HashMap<Integer,BigDecimal>();
		BigDecimal actualWeightMapWeight = new BigDecimal(0);
		BigDecimal finalWeightMapWeight = new BigDecimal(0);
 	    for(SaleContractDetailVo s:collection){
 	      Stock stock = new Stock();	
 	      stock.setId(s.getStockid());
 	      if(!StringUtils.isEmpty(s.getSelectedIdss()))
 	      {
 	    	  
 	    	  String ids=s.getSelectedIdss();
 	    	  if(selectedIdss.length()==0)
 	    	  {
 	    		 selectedIdss.append(ids);
 	    	  }
 	    	  else {
				selectedIdss.append(",");
				selectedIdss.append(ids);
			}
 	    	 
 	    	 
 	      }
 	      else
		  {
			  stock.setStatus(EnumStockStatus.OUTSTOCKING.getText());
			  stockList.add(stock);
		  }

 		  BigDecimal amount = new BigDecimal(0);
  		  amount = s.getFinalweight().multiply(s.getPrice()).setScale(3,BigDecimal.ROUND_HALF_UP);
          //amount = s.getFinalweight().multiply(s.getPrice()).multiply(new BigDecimal(s.getNum())).setScale(3,BigDecimal.ROUND_HALF_UP);
  		  totalWeight = totalWeight.add(s.getFinalweight());
  		  totalAmount = totalAmount.add(amount);
  		  SaleContractDetail  newsaleContractDetail = new SaleContractDetail();
//  		  if(actualWeightMap.containsKey(s.getId()))
//		  {
//			  actualWeightMapWeight = actualWeightMap.get(s.getId()).add(s.getActualweight());
//			  finalWeightMapWeight = finalWeightMap.get(s.getId()).add(s.getFinalweight());
//		  }
//  		  else
//
//		  {
//			  actualWeightMapWeight = s.getActualweight();
//			  finalWeightMapWeight = s.getFinalweight();
//			  actualWeightMap.put(s.getId(),actualWeightMapWeight);
//			  finalWeightMap.put(s.getId(),finalWeightMapWeight);
//
//		  }
  		  actualWeightMapWeight = s.getActualweight();
  		  finalWeightMapWeight = s.getFinalweight();
  		  newsaleContractDetail.setActualweight(actualWeightMapWeight);
  		  newsaleContractDetail.setFinalweight(finalWeightMapWeight);
  		  newsaleContractDetail.setId(s.getId());
  		  newsaleContractDetail.setDeliverystatus(EnumStockStatus.OUTSTOCKING.getText());
//  		  newsaleContractDetail.setStockid(s.getStockid());
  		  saleContractDetailList.add(newsaleContractDetail);

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
 	    if(stockList.size()>0) {
			stockService.updateBatchById(stockList);
		}
 	    saleContractDetailService.updateBatchBySd(saleContractDetailList);
 	    SaleContract saleContract = new SaleContract();
 	    saleContract.setContractno(deliveryOrderVo.getContractno());
 	    saleContract.setActualamount(totalAmount);
 	    saleContract.setActualweight(totalWeight);
 	    saleContractService.updateByContract(saleContract);
 	    if(selectedIdss.length()>0)
 	    {
 	    	ProcessOrderDetailFinish processOrderDetailFinish = new ProcessOrderDetailFinish();
 	 	    EntityWrapper<ProcessOrderDetailFinish> processOrderDetailFinishEntityWrapper = new EntityWrapper<ProcessOrderDetailFinish>();
 	 	   processOrderDetailFinishEntityWrapper.in("id", selectedIdss.toString());
 	 	   processOrderDetailFinish.setDeliveryno(deliveryno);
 	 	   processOrderDetailFinish.setDeliverystatus(EnumStockStatus.OUTSTOCKING.getText());
 	 	   processOrderDetailFinishMapper.update(processOrderDetailFinish, processOrderDetailFinishEntityWrapper);
 	    }
 	   
 	    
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
		
		wrapper.in("deliveryNo", deliveryNos);
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
		EntityWrapper<TransportOrder> tWrapper = new EntityWrapper<TransportOrder>();
		tWrapper.in("deliveryNo", deliveryOrderNos);
		List<TransportOrder> transportOrderList = transportOrderMapper.selectList(tWrapper);
		if(transportOrderList.size()>0)
		{
			throw new MyException(ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "关联送货单已存在，删除失败！如要删除，请先删除关联送货单"));
		}

    	 List<String> saleDetailIdList = new ArrayList<String>();
    	 List<String> stockIdList = new ArrayList<String>();
    	 List<Stock> stockList = new ArrayList<Stock>();
		List<DeliveryOrderDetail> deliveryOrderDetailListExt = new ArrayList<DeliveryOrderDetail>();
    	 for(String sd :Arrays.asList(deliveryOrderNos))
    	 {
    		 DeliveryOrderDetail  deliveryOrderDetail = new DeliveryOrderDetail();
    		 deliveryOrderDetail.setDeliveryno(sd);
    		 EntityWrapper<DeliveryOrderDetail> eWrapper = new EntityWrapper<DeliveryOrderDetail>(deliveryOrderDetail);
    		 List<DeliveryOrderDetail> deliveryOrderDetailList = deliveryOrderDetailService.selectList(eWrapper);
			 deliveryOrderDetailListExt.addAll(deliveryOrderDetailList);
    		 if(deliveryOrderDetailList.size()>0)
    		 {
    			 for (DeliveryOrderDetail pod:deliveryOrderDetailList)
    			 {
    				 saleDetailIdList.add(pod.getSaledetailid());
					 EntityWrapper<ProcessOrderDetailFinish> podfWrapper = new EntityWrapper<ProcessOrderDetailFinish>();
					 podfWrapper.eq("stockid",pod.getStockid());
					 List<ProcessOrderDetailFinish> processOrderDetailFinishList =  processOrderDetailFinishMapper.selectList(podfWrapper);

    				 if(processOrderDetailFinishList.size()>0)
    				 {
    					 Stock stock = new Stock();
        				 stock.setId(pod.getStockid());
         				 stock.setStatus(EnumStockStatus.PROCESSFINISH.getText());
         				 stockList.add(stock);
    				 }
    				 else{
    					 Stock stock = new Stock();
        				 stock.setId(pod.getStockid());
         				 stock.setStatus(EnumStockStatus.LOCKSTOCK.getText());
         				 stockList.add(stock);
					}
    				 stockIdList.add(pod.getStockid().toString());
//    				
    			 }
    		 }
    		 
    	 }
    	 if(stockIdList.size()>0&&stockList.size()>0)
    	 {
    		 stockService.updateBatchById(stockList);
    	 }

//    	 List<SaleContractDto> finaList = saleContractDetailService.selectByStockIdList(stockIdList);
		 List<SaleContractDto> finaList = baseMapper.selectByDeliList(Arrays.asList(deliveryOrderNos));
    	 saleContractService.batchWeigtAmountUpdate(finaList);
    	 saleContractDetailService.batchDeliveryOrderUpdate(deliveryOrderDetailListExt);
    	 ProcessOrderDetailFinish processOrderDetailFinish = new ProcessOrderDetailFinish();
  	     EntityWrapper<ProcessOrderDetailFinish> processOrderDetailFinishEntityWrapper = new EntityWrapper<ProcessOrderDetailFinish>();
  	     processOrderDetailFinishEntityWrapper.in("deliveryNo", deliveryOrderNos);
  	     processOrderDetailFinish.setDeliverystatus("");
  	     processOrderDetailFinish.setDeliveryno("");
  	     processOrderDetailFinishMapper.update(processOrderDetailFinish, processOrderDetailFinishEntityWrapper);
  	     deliveryOrderDetailService.deleteBatchDeliveryOrderNos(Arrays.asList(deliveryOrderNos));
		 super.baseMapper.deleteBatchIds(Arrays.asList(dto.getIds()));
    	 return true;
	}



	@Override
	public Integer updateBatchByDeliveryOrder(List<DeliveryOrder> deliveryOrderList) {
		// TODO Auto-generated method stub
		return deliveryOrderMapper.updateBatchByDeliveryOrder(deliveryOrderList);
	}

	@Override
	public void batchUpdateBalance(List<DeliveryOrderDetailPurDto> dlolist) {
           deliveryOrderMapper.batchUpdateBalance(dlolist);
	}


}
