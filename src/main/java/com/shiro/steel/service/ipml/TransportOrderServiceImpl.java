package com.shiro.steel.service.ipml;

import java.math.BigDecimal;
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
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumStockStatus;
import com.shiro.steel.Enum.EnumTransportFee;
import com.shiro.steel.entity.DeliveryOrder;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.entity.TransportOrder;
import com.shiro.steel.entity.TransportOrderDetail;
import com.shiro.steel.exception.MyException;
import com.shiro.steel.mapper.TransportOrderMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.TransportOrderDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.SaleContractDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderVo;
import com.shiro.steel.service.DeliveryOrderService;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.service.StockService;
import com.shiro.steel.service.TransportOrderDetailService;
import com.shiro.steel.service.TransportOrderService;
import com.shiro.steel.utils.CommonUtil;

@Service
public class TransportOrderServiceImpl extends ServiceImpl<TransportOrderMapper, TransportOrder> implements TransportOrderService {
	@Autowired 
	private TransportOrderDetailService transportOrderDetailService;
	
	@Autowired
	private SaleContractDetailService saleContractDetailService;
	
	@Autowired
	private DeliveryOrderService deliveryOrderService;
	
	@Autowired
	private StockService stockService;
	
	final static String preName = "YS";
	@Override
	public Boolean addTransportOrder(TransportOrderVo transportOrderVo) {
		// TODO Auto-generated method stub
		String transportOrderNo = "";
		StringBuffer stockidBuffer = new StringBuffer();
		String transportOrderDetail = transportOrderVo.getTransportOrderDetail();
		TransportOrder transportOrder = new TransportOrder();
 	    BeanCopier copier = BeanCopier.create(TransportOrderVo.class, TransportOrder.class, false);
 		copier.copy(transportOrderVo, transportOrder, null);
 		transportOrder.setCrt(new Date());
 	    UserInfoDto userInfoDto = new UserInfoDto();
 	    Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
	    transportOrderNo = preName+CommonUtil.getTimeStamp();
	    transportOrder.setTransportno(transportOrderNo);
	    transportOrder.setCrt(new Date());
	    transportOrder.setCreateby(userInfoDto.getUsername());
        transportOrder.setStatus(EnumStockStatus.TRANSPORTING.getText());
 	    List<SaleContractDetailVo>  collection = JSONObject.parseArray(transportOrderDetail, SaleContractDetailVo.class);
 	    BigDecimal totalWeight = new BigDecimal(0);
 	    BigDecimal actualWeight = new BigDecimal(0);
 	    List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
 	    List<Stock> stockList = new ArrayList<Stock>();
 	    for(SaleContractDetailVo s:collection){
 	      Stock stock = new Stock();	
 	      stock.setId(s.getStockid());
 	      stock.setStatus(EnumStockStatus.OUTSTOCKFINISH.getText());
 		  BigDecimal amount = new BigDecimal(0);
  		  totalWeight = totalWeight.add(s.getFinalweight());
  		  actualWeight = actualWeight.add(s.getActualweight());
  		  SaleContractDetail  newsaleContractDetail = new SaleContractDetail();
//  		  newsaleContractDetail.setId(Integer.valueOf(s.getSaledetailid()));
		  newsaleContractDetail.setStockid(s.getStockid());
  		  
  		
  		  newsaleContractDetail.setDeliverystatus(EnumStockStatus.OUTSTOCKFINISH.getText());
  		  newsaleContractDetail.setTransportstatus(EnumStockStatus.TRANSPORTING.getText());
  		  saleContractDetailList.add(newsaleContractDetail);
  		  stockList.add(stock);
  	   }
 	    List<TransportOrderDetail>  transportOrderDetailList = JSONObject.parseArray(transportOrderDetail, TransportOrderDetail.class);
 	    for(TransportOrderDetail s:transportOrderDetailList){
 		   s.setTransportno(transportOrderNo);
 		   s.setCrt(new Date());
 		   if(s.getId()!=null)
 		   {
 	 		   s.setSaledetailid(s.getSaledetailid());
 		   }

 		   s.setId(null);
 	   }
 	   transportOrder.setTransportweight(actualWeight);
 	   transportOrder.setDeliveryno(transportOrderVo.getDeliveryno());
	    if(EnumTransportFee.TAXFREE.getValue()==(transportOrderVo.getFeeoption())|| EnumTransportFee.TAXINCLUDED.getValue()==(transportOrderVo.getFeeoption()) )
	    {
	    	
	    	transportOrder.setTransporttotalfee(transportOrderVo.getTransportfee().multiply(actualWeight));
	    }
	    else
	    {
	    	transportOrder.setTransporttotalfee(transportOrderVo.getTransportfee());
	    }
	    String[] deliveryOrderString = transportOrderVo.getDeliveryno().split(",");
	    List<DeliveryOrder> deliveryOrderList = new ArrayList<DeliveryOrder>();
	    for(String deliverOrder:deliveryOrderString)
	    {
	    	DeliveryOrder deliveryOrder = new DeliveryOrder();
	    	deliveryOrder.setDeliveryno(deliverOrder);
	    	deliveryOrder.setStatus(EnumStockStatus.OUTSTOCKFINISH.getText());
	    	deliveryOrderList.add(deliveryOrder);
	    }
	    deliveryOrderService.updateBatchByDeliveryOrder(deliveryOrderList);
 	    super.baseMapper.insert(transportOrder);
 	    stockService.updateBatchById(stockList);
 	    transportOrderDetailService.insertBatch(transportOrderDetailList);
 	    Boolean status =saleContractDetailService.updateBatchByEntity(saleContractDetailList);
		return status;
	}
	@Override
	public List<TransportOrderDetailVo> findDetailByPageList(ParamsDto dto, String memberId, String transportNo) {
		// TODO Auto-generated method stub
		List<TransportOrderDetailVo> list = super.baseMapper.findDetailByPageList(memberId,transportNo);
        return list;
	}
	@Override
	public List<TransportOrderDto> findTransportOrderByPage(Page<TransportOrderDto> page, ParamsDto dto,
			String createby,String memberId ,String carrier, String startTimeString, String endTimeString) {
		// TODO Auto-generated method stub
		return super.baseMapper.findTransportOrderByPage(page, dto,createby,memberId,carrier,startTimeString,endTimeString);
	}
	@Override
	public Boolean delTransportOrder(ParamsDto dto, String[] transportOrderIds, String[] saleContractNos,String[] deliveryOrderNos) throws MyException{
		// TODO Auto-generated method stub
			 super.baseMapper.deleteBatchIds(Arrays.asList(dto.getIds()));
//		   	 List<String> saleDetailIdList = new ArrayList<String>();
		   	 List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
		   	 for(String sd :Arrays.asList(transportOrderIds))
		   	 {
		   		 TransportOrderDetail  transportOrderDetail = new TransportOrderDetail();
		   		 transportOrderDetail.setTransportno(sd);
		   		 EntityWrapper<TransportOrderDetail> eWrapper = new EntityWrapper<TransportOrderDetail>(transportOrderDetail);
		   		 List<TransportOrderDetail> transportOrderDetailList = transportOrderDetailService.selectList(eWrapper);
		   		 
		   		 if(transportOrderDetailList.size()>0)
		   		 {
		   			 for (TransportOrderDetail pod:transportOrderDetailList)
		   			 {
		   			  SaleContractDetail  newsaleContractDetail = new SaleContractDetail();
		  		      newsaleContractDetail.setStockid(pod.getStockid());
		    		  
		    		
		    		  newsaleContractDetail.setDeliverystatus(EnumStockStatus.OUTSTOCKING.getText());
		    		  newsaleContractDetail.setTransportstatus("");
//		   				 saleDetailIdList.add(pod.getSaledetailid());
		    		  saleContractDetailList.add(newsaleContractDetail);
		   			 }
		   		 }
		   		 
		   	 }
		   	 EntityWrapper<DeliveryOrder> deliveryOrderWrapper = new EntityWrapper<DeliveryOrder>();
		   	 String deliveryOrderNosString= StringUtils.join(deliveryOrderNos,",");
		     deliveryOrderWrapper.in("deliveryNo", deliveryOrderNosString);
		     DeliveryOrder deliveryOrderEntity = new DeliveryOrder();
		     deliveryOrderEntity.setStatus(EnumStockStatus.OUTSTOCKING.getText());
		     deliveryOrderService.update(deliveryOrderEntity, deliveryOrderWrapper);
		   	 transportOrderDetailService.deleteBatchTransportOrderNos(Arrays.asList(transportOrderIds));
		   	 saleContractDetailService.updateBatchByEntity(saleContractDetailList);
		    return true;
		}
	
}