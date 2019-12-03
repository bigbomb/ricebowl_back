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
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.entity.TransportOrder;
import com.shiro.steel.entity.TransportOrderDetail;
import com.shiro.steel.mapper.TransportOrderMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.TransportOrderDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderVo;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.service.StockService;
import com.shiro.steel.service.TransportOrderDetailService;
import com.shiro.steel.service.TransportOrderService;
import com.shiro.steel.utils.GeneratorUtil;

@Service
public class TransportOrderServiceImpl extends ServiceImpl<TransportOrderMapper, TransportOrder> implements TransportOrderService {
	@Autowired 
	private TransportOrderDetailService transportOrderDetailService;
	
	@Autowired
	private SaleContractDetailService saleContractDetailService;
	
	
	@Autowired
	private StockService stockService;
	
	final static String preName = "YS";
	@Override
	public Boolean addTransportOrder(TransportOrderVo transportOrderVo) {
		// TODO Auto-generated method stub
		String transportOrderNo = "";
		String transportOrderDetail = transportOrderVo.getTransportOrderDetail();
		TransportOrder transportOrder = new TransportOrder();
 	    BeanCopier copier = BeanCopier.create(TransportOrderVo.class, TransportOrder.class, false);
 		copier.copy(transportOrderVo, transportOrder, null);
 		transportOrder.setCrt(new Date());
 	    UserInfoDto userInfoDto = new UserInfoDto();
 	    Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
	    transportOrderNo = preName+GeneratorUtil.getTimeStamp();
	    transportOrder.setTransportno(transportOrderNo);
	    transportOrder.setCrt(new Date());
	    transportOrder.setCreateby(userInfoDto.getUsername());
 	    super.baseMapper.insert(transportOrder);
 	    List<SaleContractDetail>  collection = JSONObject.parseArray(transportOrderDetail, SaleContractDetail.class);
 	    BigDecimal totalWeight = new BigDecimal(0);
 	    List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
 	    List<Stock> stockList = new ArrayList<Stock>();
 	    for(SaleContractDetail s:collection){
 	      Stock stock = new Stock();	
 	      stock.setId(s.getStockid());
 	      stock.setStatus(EnumStockStatus.OUTSTOCK.getText());
 		  BigDecimal amount = new BigDecimal(0);
  		  totalWeight = totalWeight.add(s.getFinalweight());
  		  SaleContractDetail  newsaleContractDetail = new SaleContractDetail();
  		  newsaleContractDetail.setId(s.getId());
  		  newsaleContractDetail.setTransportStatus(EnumStockStatus.TRANSPORT.getText());
  		  saleContractDetailList.add(newsaleContractDetail);
  		  stockList.add(stock);
  	   }
 	    List<TransportOrderDetail>  deliveryOrderDetailList = JSONObject.parseArray(transportOrderDetail, TransportOrderDetail.class);
 	    for(TransportOrderDetail s:deliveryOrderDetailList){
 		   s.setTransportno(transportOrderNo);
 		   s.setCrt(new Date());
 		   if(s.getId()!=null)
 		   {
 	 		   s.setSaledetailid(s.getId().toString());
 		   }

 		   s.setId(null);
 	   }
 	    stockService.updateBatchById(stockList);
 	    transportOrderDetailService.insertBatch(deliveryOrderDetailList);
 	    Boolean status =saleContractDetailService.insertOrUpdateBatch(saleContractDetailList);
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
	public Boolean delTransportOrder(ParamsDto dto, String[] transportOrderNos, String[] saleContractNos) {
		// TODO Auto-generated method stub
		try {
			 super.baseMapper.deleteBatchIds(Arrays.asList(dto.getIds()));
		   	 List<String> saleDetailIdList = new ArrayList<String>();
		   	 List<Stock> stockList = new ArrayList<Stock>();
		   	 for(String sd :Arrays.asList(transportOrderNos))
		   	 {
		   		 TransportOrderDetail  transportOrderDetail = new TransportOrderDetail();
		   		 transportOrderDetail.setTransportno(sd);
		   		 EntityWrapper<TransportOrderDetail> eWrapper = new EntityWrapper<TransportOrderDetail>(transportOrderDetail);
		   		 List<TransportOrderDetail> transportOrderDetailList = transportOrderDetailService.selectList(eWrapper);
		   		 
		   		 if(transportOrderDetailList.size()>0)
		   		 {
		   			 for (TransportOrderDetail pod:transportOrderDetailList)
		   			 {
		   				 saleDetailIdList.add(pod.getSaledetailid());
		   			 }
		   		 }
		   		 
		   	 }
		   	 transportOrderDetailService.deleteBatchTransportOrderNos(Arrays.asList(transportOrderNos));
		   	 saleContractDetailService.batchTransportOrderUpdate(Arrays.asList(saleContractNos),saleDetailIdList);
		    return true;
		}catch(Exception e)
		{
			return false;
		}
		
	}


  

	
}