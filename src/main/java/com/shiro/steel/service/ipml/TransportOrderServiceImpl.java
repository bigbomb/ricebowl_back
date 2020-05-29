package com.shiro.steel.service.ipml;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.shiro.steel.entity.*;
import com.shiro.steel.pojo.dto.*;
import com.shiro.steel.service.*;
import com.shiro.steel.utils.CollectorsUtil;
import net.sf.ehcache.util.SetAsList;
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
import com.shiro.steel.exception.MyException;
import com.shiro.steel.mapper.TransportOrderMapper;
import com.shiro.steel.pojo.vo.SaleContractDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderDetailVo;
import com.shiro.steel.pojo.vo.TransportOrderVo;
import com.shiro.steel.utils.CommonUtil;

@Service
public class TransportOrderServiceImpl extends ServiceImpl<TransportOrderMapper, TransportOrder> implements TransportOrderService {
	@Autowired 
	private TransportOrderDetailService transportOrderDetailService;
	
	@Autowired
	private SaleContractDetailService saleContractDetailService;

	@Autowired
	private SaleContractService saleContractService;

	@Autowired
	private DeliveryOrderService deliveryOrderService;

	@Autowired
	private DeliveryOrderDetailService deliveryOrderDetailService;

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
// 	    List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
 	    List<Stock> stockList = new ArrayList<Stock>();
 	    for(SaleContractDetailVo s:collection){
 	      Stock stock = new Stock();	
 	      stock.setId(s.getStockid());
 	      stock.setStatus(EnumStockStatus.OUTSTOCKFINISH.getText());
 		  BigDecimal amount = new BigDecimal(0);
  		  totalWeight = totalWeight.add(s.getFinalweight());
  		  actualWeight = actualWeight.add(s.getActualweight());

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
 	    Boolean status = transportOrderDetailService.insertBatch(transportOrderDetailList);
// 	    Boolean status =saleContractDetailService.updateBatchByEntity(saleContractDetailList);
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

	@Override
	public List<TransportOrderDto> selectListBytransport(Page<SaleContractDto> page, ParamsDto dto, String createby, String startTimeString, String endTimeString) {
		return super.baseMapper.selectListBytransport(page,dto,createby,startTimeString,endTimeString);
	}

	@Override
	public Boolean confirmTransportOrder(TransportOrderVo transportOrderVo,String actualTotalWeight) {
		TransportOrder transportOrder = new TransportOrder();
		transportOrder.setId(transportOrderVo.getId());
		transportOrder.setFeeoption(transportOrderVo.getFeeoption());
		transportOrder.setTransportweight(new BigDecimal(actualTotalWeight));
		transportOrder.setStatus(EnumStockStatus.TRANSPORTFINISH.getText());
		if (EnumTransportFee.TAXFREE.getValue() == (transportOrderVo.getFeeoption()) || EnumTransportFee.TAXINCLUDED.getValue() == (transportOrderVo.getFeeoption())) {

			transportOrder.setTransportfee(transportOrderVo.getTransportfee());
			transportOrder.setTransporttotalfee(transportOrderVo.getTransportfee().multiply(new BigDecimal(actualTotalWeight)));
		} else {
			transportOrder.setTransportfee(new BigDecimal(0));
			transportOrder.setTransporttotalfee(transportOrderVo.getTransportfee());
		}
		super.baseMapper.updateById(transportOrder);
		String transportOrderDetail = transportOrderVo.getTransportOrderDetail();
		List<TransportOrderDetail> transportOrderDetailList = JSONObject.parseArray(transportOrderDetail, TransportOrderDetail.class);
		List<DeliveryOrderDetailPurDto> deliveryOrderDetailPurDtoList = new ArrayList<DeliveryOrderDetailPurDto>();
		for (TransportOrderDetail transportOrderDetailobject : transportOrderDetailList) {
			TransportOrderDetail tod = transportOrderDetailService.selectById(transportOrderDetailobject.getId());
			if (!tod.getFinalweight().equals(transportOrderDetailobject.getFinalweight().setScale(3, BigDecimal.ROUND_HALF_UP))) {
				DeliveryOrderDetailPurDto deliveryOrderDetailPurDto = new DeliveryOrderDetailPurDto();
				deliveryOrderDetailPurDto.setBalance(transportOrderDetailobject.getFinalweight().subtract(tod.getFinalweight()));
				deliveryOrderDetailPurDto.setSaledetailid(Integer.valueOf(transportOrderDetailobject.getSaledetailid()));
				deliveryOrderDetailPurDto.setStockid(tod.getStockid());
				deliveryOrderDetailPurDto.setDeliveryno(tod.getDeliveryno());
				deliveryOrderDetailPurDtoList.add(deliveryOrderDetailPurDto);
			}

		}
		transportOrderDetailService.updateBatchById(transportOrderDetailList);
      /** 商品误差统计,按照deliveryno为group,更新的是deliveryOrder*/
		Map<String, List> balanceSumByDeliveryno = deliveryOrderDetailPurDtoList.stream()
				.collect(Collectors.groupingBy(DeliveryOrderDetailPurDto::getDeliveryno, Collectors.collectingAndThen(Collectors.toList(), m -> {
							/** balance累计*/
							final BigDecimal balance = m
									.stream()
									.collect(CollectorsUtil.summingBigDecimal(DeliveryOrderDetailPurDto::getBalance));

							return Arrays.asList(
									balance
							);
						}

				)));
		List<DeliveryOrder> deliveryOrderList = new ArrayList<DeliveryOrder>();
		balanceSumByDeliveryno.forEach((k,v) ->
				{
					System.out.println("key: " + k + " , " + "value: " + v);
					EntityWrapper ew = new EntityWrapper();
					ew.eq("deliveryNo",k);
					DeliveryOrder deliveryOrder = deliveryOrderService.selectOne(ew);
					deliveryOrder.setFinalweight(deliveryOrder.getFinalweight().add(new BigDecimal(v.get(0).toString())));
					deliveryOrderList.add(deliveryOrder);
				}
		);
		deliveryOrderService.updateBatchById(deliveryOrderList);

       /** 商品误差统计,按照stockid为group,更新的是deliveryOrderDetail*/
		Map<Integer, List> balanceSumByStockid = deliveryOrderDetailPurDtoList.stream()
				.collect(Collectors.groupingBy(DeliveryOrderDetailPurDto::getStockid, Collectors.collectingAndThen(Collectors.toList(), m -> {
							/** balance累计*/
							final BigDecimal balance = m
									.stream()
									.collect(CollectorsUtil.summingBigDecimal(DeliveryOrderDetailPurDto::getBalance));

							return Arrays.asList(
									balance
							);
						}

				)));
		List<DeliveryOrderDetail> deliveryOrderDetailList = new ArrayList<DeliveryOrderDetail>();
		balanceSumByStockid.forEach((k,v) ->
				{
					System.out.println("key: " + k + " , " + "value: " + v);
					EntityWrapper ew = new EntityWrapper();
					ew.eq("stockId",k);
					DeliveryOrderDetail deliveryOrderDetail = deliveryOrderDetailService.selectOne(ew);
					deliveryOrderDetail.setFinalweight(deliveryOrderDetail.getFinalweight().add(new BigDecimal(v.get(0).toString())));
					deliveryOrderDetailList.add(deliveryOrderDetail);
				}
		);
		deliveryOrderDetailService.updateBatchById(deliveryOrderDetailList);

		/** 商品误差统计,按照saleid为group键值，更新的是saleContractDetail*/
		Map<Integer, List> balanceSumBySaleid = deliveryOrderDetailPurDtoList.stream()
				.collect(Collectors.groupingBy(DeliveryOrderDetailPurDto::getSaledetailid, Collectors.collectingAndThen(Collectors.toList(), m -> {
					/** balance累计*/
					final BigDecimal balance = m
							.stream()
							.collect(CollectorsUtil.summingBigDecimal(DeliveryOrderDetailPurDto::getBalance));

					return Arrays.asList(
							balance
					);
				}

				)));
		List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
		List<SaleContractDetailDto> saleContractDetailDtoList = new ArrayList<SaleContractDetailDto>();
		balanceSumBySaleid.forEach((k,v) ->
				{
					System.out.println("key: " + k + " , " + "value: " + v);
					SaleContractDetail saleContractDetail = saleContractDetailService.selectById(k);
					saleContractDetail.setFinalweight(saleContractDetail.getFinalweight().add(new BigDecimal(v.get(0).toString())));
					saleContractDetailList.add(saleContractDetail);
					SaleContractDetailDto saleContractDetailDto = new SaleContractDetailDto();
					saleContractDetailDto.setContractno(saleContractDetail.getContractno());
					saleContractDetailDto.setBalance(new BigDecimal(v.get(0).toString()));
//					saleContractDetailDto.setPrice(saleContractDetail.getPrice());
					saleContractDetailDto.setBalanceamount(new BigDecimal(v.get(0).toString()).multiply(saleContractDetail.getPrice()));
					saleContractDetailDtoList.add(saleContractDetailDto);
				}
		);
		saleContractDetailService.updateBatchById(saleContractDetailList);
		/** 商品误差统计,按照contractno为group键值，更新的是saleContract*/
		Map<String, List> contractFinalSum = saleContractDetailDtoList.stream()
				.collect(Collectors.groupingBy(SaleContractDetailDto::getContractno, Collectors.collectingAndThen(Collectors.toList(), m -> {
							/** finalweight累计*/
							final BigDecimal finalbalance= m
									.stream()
									.collect(CollectorsUtil.summingBigDecimal(SaleContractDetailDto::getBalance));

							final BigDecimal finalbalanceAmount = m
									.stream()
									.collect(CollectorsUtil.summingBigDecimal(SaleContractDetailDto::getBalanceamount));

					        /** finalamount累计*/
							return Arrays.asList(
									finalbalance,
									finalbalanceAmount
							);
						}

				)));
		List<SaleContract> saleContractlist = new ArrayList<SaleContract>();
		contractFinalSum.forEach((k,v) ->
		{
			System.out.println("key: " + k + " , " + "value: " + v);
			EntityWrapper entityWrapper = new EntityWrapper();
			entityWrapper.eq("contractNo",k);
			SaleContract saleContract = saleContractService.selectOne(entityWrapper);
			saleContract.setActualweight(saleContract.getActualweight().add(new BigDecimal(v.get(0).toString())));
			saleContract.setActualamount(saleContract.getActualamount().add(new BigDecimal(v.get(1).toString())));
			saleContractlist.add(saleContract);
		});
		saleContractService.updateBatchById(saleContractlist);
		return true;
	}

}