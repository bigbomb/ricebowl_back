package com.shiro.steel.service.ipml;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.shiro.steel.entity.*;
import com.shiro.steel.exception.MyException;
import com.shiro.steel.pojo.dto.*;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;
import com.shiro.steel.service.*;
import com.shiro.steel.utils.CollectorsUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.mapper.PurchaseContractMapper;
import com.shiro.steel.pojo.vo.ContractVo;
import com.shiro.steel.pojo.vo.PurchaseContractVo;
import com.shiro.steel.utils.CommonUtil;
import com.shiro.steel.utils.ResultUtil;
@Service
public class PurchaseContractServiceImpl extends ServiceImpl<PurchaseContractMapper, PurchaseContract> implements PurchaseContractService{
	
	@Autowired
	private PurchaseContractDetailService purchaseContractDetailService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductFactoryService productFactoryService;
	
	@Autowired
	private ProductMarkService productMarkService;
	
	@Autowired
	private ProductSpecService productSpecService;

	@Autowired
	private SaleContractWarehouseService saleContractWarehouseService;
	
	@Autowired
	private StockService stockService;

	@Autowired
	private DeliveryOrderDetailService deliveryOrderDetailService;

	@Autowired
	private DeliveryOrderService deliveryOrderService;

	@Autowired
	private SaleContractDetailService saleContractDetailService;

	@Autowired
	private TransportOrderDetailService transportOrderDetailService;
	final static String preName = "cg";
	@Override
	public List<PurchaseContractDto> findPurchaseContractByStatusPage(Page<PurchaseContractDto> page, ParamsDto dto,String statusTab,String invoiceStatus,String createby, String startTime,String endTime) {
		// TODO Auto-generated method stub
		return super.baseMapper.findPurchaseContractByStatusPage(page, dto,statusTab,invoiceStatus,createby,startTime,endTime);
	}

	
	@Override
	public List<PurchaseContractDto> findPurchaseContractByPage(Page<PurchaseContractDto> page, ParamsDto dto) {
		// TODO Auto-generated method stub
		return super.baseMapper.findPurchaseContractByPage(page, dto);
	}
	
	
	@Override
	public Object addContract(PurchaseContractVo purchaseContractVo) throws ParseException {
		// TODO Auto-generated method stub
		String contractno = "";
    	//contractVo.setContractstatus(ContractStatus.TOPAY.getText());
    	String purchaseContractDetail = purchaseContractVo.getPurchaseContractDetail();
    	List<PurchaseContractDetail>  collection = JSONObject.parseArray(purchaseContractDetail, PurchaseContractDetail.class);
    	String status ="";
		PurchaseContract purchaseContract = new PurchaseContract();
		BeanCopier copier = BeanCopier.create(PurchaseContractVo.class, PurchaseContract.class, false);
		copier.copy(purchaseContractVo, purchaseContract, null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(StringUtils.isEmpty(purchaseContractVo.getPurchaseno()))
			{
				contractno = preName+CommonUtil.getTimeStamp();
				purchaseContract.setPurchaseno(contractno);
				List<Product> productList = new ArrayList<Product>();
				List<Productfactory> productfactoryList = new ArrayList<Productfactory>();
				List<Productmark> productmarkList = new ArrayList<Productmark>();
				List<Productspec> productspecList = new ArrayList<Productspec>();
				List<SaleContractWarehouse> saleContractWarehouseList = new ArrayList<SaleContractWarehouse>();
				List checkProduct = new ArrayList();
		    	for(PurchaseContractDetail s:collection){
		    		Product product = new Product();
					Productfactory productfactory = new Productfactory();
					Productmark productmark = new Productmark();
					Productspec productspec = new Productspec();
				   SaleContractWarehouse saleContractWarehouse = new SaleContractWarehouse();
		  		   s.setPurchaseno(contractno);
		  		   s.setStatus("待审核");
		  		   EntityWrapper<Product> eWrapper = new EntityWrapper<Product>(product);
	  		 	   product.setProductname(s.getProductname());
	  		 	   product.setMemberid(purchaseContractVo.getMemberid());
	  		       product = productService.selectOne(eWrapper);
	  		       if(product == null)
	  		       {
	  		    	 product = new Product();
	  		    	 product.setProductname(s.getProductname());
		  		 	 product.setMemberid(purchaseContractVo.getMemberid());
		  		 	 if(!checkProduct.contains(s.getProductname()))
		 				 {
		  		 	    checkProduct.add(s.getProductname());
		  			    productList.add(product);
 		 			 }
		  		 	 else
		  		 	 {
		  		 		continue;
		  		 	 }
		  	      }
	  		     EntityWrapper<Productfactory> wapperFactory = new EntityWrapper<Productfactory>(productfactory);
	  		      productfactory.setFactoryname(s.getProductfactory());
	  		      productfactory.setMemberid(purchaseContractVo.getMemberid());
	  		      productfactory = productFactoryService.selectOne(wapperFactory);
	  		      if(productfactory == null)
	  		      {
	  		    	productfactory = new Productfactory();
	  		    	productfactory.setFactoryname(s.getProductfactory());
	  		    	productfactory.setMemberid(purchaseContractVo.getMemberid());
	  		    	
	  		    	if(!checkProduct.contains(s.getProductfactory()))
		 				{
		  		 	  checkProduct.add(s.getProductfactory());
		  		      productfactoryList.add(productfactory);
 		 			}
		  		 	else
		  		 	{
		  		 		continue;
		  		 	}
	  		    	
	  		      }
	  		      EntityWrapper<Productmark> wrapperMark = new EntityWrapper<Productmark>(productmark);
 		      	  productmark.setMarkname(s.getProductmark());
 		      	  productmark.setMemberid(purchaseContractVo.getMemberid());
 		      	  productmark = productMarkService.selectOne(wrapperMark);
	  		      if(productmark == null)
	  		      {
	  		    	productmark = new Productmark();
	  		    	productmark.setMarkname(s.getProductmark());
	  		    	productmark.setMemberid(purchaseContractVo.getMemberid());
	  		    	if(!checkProduct.contains(s.getProductmark()))
		 				{
		  		 	  checkProduct.add(s.getProductmark());
		  		      productmarkList.add(productmark);
 		 			}
		  		 	else
		  		 	{
		  		 		continue;
		  		 	}
	  		      }
		  		
	  		      EntityWrapper<Productspec> wrapperSpec = new EntityWrapper<Productspec>(productspec);
	  		      productspec.setSpecname(s.getProductspec());
	  		      productspec.setMemberid(purchaseContractVo.getMemberid());
	  		      productspec = productSpecService.selectOne(wrapperSpec);
	  		      if(productspec == null)
	  		      {
	  		    	productspec = new Productspec();
	  		    	productspec.setSpecname(s.getProductspec());
	  		    	productspec.setMemberid(purchaseContractVo.getMemberid());
	  		    	if(!checkProduct.contains(s.getProductspec()))
		 				{
		  		 	  checkProduct.add(s.getProductspec());
		  		      productspecList.add(productspec);
 		 			}
		  		 	else
		  		 	{
		  		 		continue;
		  		 	}
	  		      }
	  		      

	  		      EntityWrapper<SaleContractWarehouse> SaleContractWarehouseWapper = new EntityWrapper<SaleContractWarehouse>(saleContractWarehouse);
	  		      saleContractWarehouse.setWarehousename(s.getWarehousename());
	  		      saleContractWarehouse.setMemberid(purchaseContractVo.getMemberid());
	  		      saleContractWarehouse = saleContractWarehouseService.selectOne(SaleContractWarehouseWapper);
	  		      if(saleContractWarehouse == null)
	  		      {
	  		    	saleContractWarehouse = new SaleContractWarehouse();
	  		    	saleContractWarehouse.setWarehousename(s.getWarehousename());
		  		    saleContractWarehouse.setMemberid(purchaseContractVo.getMemberid());
	  		    	
	  		    	if(!checkProduct.contains(s.getWarehousename()))
	 				{
		  		 	  checkProduct.add(s.getWarehousename());
		  		 	  saleContractWarehouseList.add(saleContractWarehouse);
  		 			}
		  		 	else
		  		 	{
		  		 		continue;
		  		 	}
	  		    	
	  		      }
	  		      
	  		      
		    	}
		    	if(productList.size()>0)
		    	{
			    	productService.insertBatch(productList);
		    	}
		    	if(productfactoryList.size()>0)
		    	{
		    		productFactoryService.insertBatch(productfactoryList);
		    	}
		    	if(productmarkList.size()>0)
		    	{
		    		productMarkService.insertBatch(productmarkList);
		    	}
		    	if(productspecList.size()>0)
		    	{
		    		productSpecService.insertBatch(productspecList);
		    	}
		    	if(saleContractWarehouseList.size()>0)
		    	{
		    		saleContractWarehouseService.insertBatch(saleContractWarehouseList);
		    	}
		    	UserInfoDto userInfoDto = new UserInfoDto();
		        Subject subject = SecurityUtils.getSubject();   
			    userInfoDto = (UserInfoDto) subject.getPrincipal();
			    purchaseContractVo.setMemberid(purchaseContractVo.getMemberid());
			    purchaseContract.setPurchasedate(sdf.parse(sdf.format(new Date())));
			    purchaseContract.setCrt(sdf1.parse(sdf1.format(new Date())));
			    purchaseContract.setPurchasestatus("待审核");
			    purchaseContract.setInvoicestatus("未收到");
			    purchaseContract.setCreateby(userInfoDto.getUsername());
				super.baseMapper.insert(purchaseContract);
				purchaseContractDetailService.insertBatch(collection);
				status = "新增成功";
			}
			else
			{
				List<Product> productList = new ArrayList<Product>();
				List<Productfactory> productfactoryList = new ArrayList<Productfactory>();
				List<Productmark> productmarkList = new ArrayList<Productmark>();
				List<Productspec> productspecList = new ArrayList<Productspec>();
				List<SaleContractWarehouse> saleContractWarehouseList = new ArrayList<SaleContractWarehouse>();
				List<Stock> stockList = new ArrayList<Stock>();

				 List checkProduct = new ArrayList();
				purchaseContract.setUpt(sdf1.parse(sdf1.format(new Date())));
				super.baseMapper.updateByPrimaryKey(purchaseContract);
//				Map contractMap = new HashMap();
//				contractMap.put("purchaseNo", purchaseContract.getPurchaseno());
//				purchaseContractDetailService.deleteByMap(contractMap);
				for(PurchaseContractDetail s:collection){
					if(purchaseContractVo.getChange()==1)
					{
						Stock stock = new Stock();
						stock.setPurdetailid(s.getId());
						stock.setPackingno(s.getPackingno());
						stock.setWeight(s.getWeight());
						stock.setUnit(s.getUnit());
						stockList.add(stock);
					}

					Product product = new Product();
					Productfactory productfactory = new Productfactory();
					Productmark productmark = new Productmark();
					Productspec productspec = new Productspec();
					SaleContractWarehouse saleContractWarehouse = new SaleContractWarehouse();
			  		  s.setPurchaseno(purchaseContract.getPurchaseno());  
//			  		  s.setStatus("在库");
			  		  EntityWrapper<Product> eWrapper = new EntityWrapper<Product>(product);
		  		 	  product.setProductname(s.getProductname());
		  		 	  product.setMemberid(purchaseContractVo.getMemberid());
		  		      product = productService.selectOne(eWrapper);
		  		      if(product == null)
		  		      {
		  		    	product = new Product();
		  		    	product.setProductname(s.getProductname());
			  		 	product.setMemberid(purchaseContractVo.getMemberid());
			  		 	if(!checkProduct.contains(s.getProductname()))
 		 				{
			  		 	  checkProduct.add(s.getProductname());
			  			  productList.add(product);
	  		 			}
			  		 	else
			  		 	{
			  		 		continue;
			  		 	}
		  		      }
		  		    EntityWrapper<Productfactory> wapperFactory = new EntityWrapper<Productfactory>(productfactory);
		  		      productfactory.setFactoryname(s.getProductfactory());
		  		      productfactory.setMemberid(purchaseContractVo.getMemberid());
		  		      productfactory = productFactoryService.selectOne(wapperFactory);
		  		      if(productfactory == null)
		  		      {
		  		    	productfactory = new Productfactory();
		  		    	productfactory.setFactoryname(s.getProductfactory());
		  		    	productfactory.setMemberid(purchaseContractVo.getMemberid());
			  		   	if(!checkProduct.contains(s.getProductfactory()))
			 				{
			  		 	  checkProduct.add(s.getProductfactory());
			  			  productfactoryList.add(productfactory);
	  		 			}
			  		 	else
			  		 	{
			  		 		continue;
			  		 	}
		  		   	
		  		    
		  		      }
		  		      EntityWrapper<Productmark> wrapperMark = new EntityWrapper<Productmark>(productmark);
	  		      	  productmark.setMarkname(s.getProductmark());
	  		      	  productmark.setMemberid(purchaseContractVo.getMemberid());
	  		      	  productmark = productMarkService.selectOne(wrapperMark);
		  		      if(productmark == null)
		  		      {
		  		    	productmark = new Productmark();
		  		    	productmark.setMarkname(s.getProductmark());
		  		    	productmark.setMemberid(purchaseContractVo.getMemberid());
		  		    	if(!checkProduct.contains(s.getProductmark()))
		 				{
			  		 	  checkProduct.add(s.getProductmark());
			  		 	  productmarkList.add(productmark);
	  		 			}
			  		 	else
			  		 	{
			  		 		continue;
			  		 	}
		  		      }
			  		
		  		      EntityWrapper<Productspec> wrapperSpec = new EntityWrapper<Productspec>(productspec);
		  		      productspec.setSpecname(s.getProductspec());
		  		      productspec.setMemberid(purchaseContractVo.getMemberid());
		  		      productspec = productSpecService.selectOne(wrapperSpec);
		  		      if(productspec == null)
		  		      {
		  		    	productspec = new Productspec();
		  		    	productspec.setSpecname(s.getProductspec());
		  		    	productspec.setMemberid(purchaseContractVo.getMemberid());
			  		  	if(!checkProduct.contains(s.getProductspec()))
		 				{
			  		 	  checkProduct.add(s.getProductspec());
			  		 	  productspecList.add(productspec);
			 			}
			  		 	else
			  		 	{
			  		 		continue;
			  		 	}
		  		      }
		  		      
		  		    EntityWrapper<SaleContractWarehouse> SaleContractWarehouseWapper = new EntityWrapper<SaleContractWarehouse>(saleContractWarehouse);
		  		      saleContractWarehouse.setWarehousename(s.getWarehousename());
		  		      saleContractWarehouse.setMemberid(purchaseContractVo.getMemberid());
		  		      saleContractWarehouse = saleContractWarehouseService.selectOne(SaleContractWarehouseWapper);
		  		      if(saleContractWarehouse == null)
		  		      {
		  		    	saleContractWarehouse = new SaleContractWarehouse();
		  		    	saleContractWarehouse.setWarehousename(s.getWarehousename());
			  		    saleContractWarehouse.setMemberid(purchaseContractVo.getMemberid());
		  		    	
		  		    	if(!checkProduct.contains(s.getWarehousename()))
		 				{
			  		 	  checkProduct.add(s.getWarehousename());
			  		 	  saleContractWarehouseList.add(saleContractWarehouse);
	  		 			}
			  		 	else
			  		 	{
			  		 		continue;
			  		 	}
		  		    	
		  		      }
			  	  }
				if(productList.size()>0)
		    	{
			    	productService.insertBatch(productList);
		    	}
				if(productfactoryList.size()>0)
		    	{
		    		productFactoryService.insertBatch(productfactoryList);
		    	}
		    	if(productmarkList.size()>0)
		    	{
		    		productMarkService.insertBatch(productmarkList);
		    	}
		    	if(productspecList.size()>0)
		    	{
		    		productSpecService.insertBatch(productspecList);
		    	}
		    	if(saleContractWarehouseList.size()>0)
		    	{
		    		saleContractWarehouseService.insertBatch(saleContractWarehouseList);
		    	}

		    	if(stockList.size()>0)
				{

					Boolean st = stockService.batchUpdatebyPurId(stockList);
					List<DeliveryOrderDetail> dodList = new ArrayList<DeliveryOrderDetail>();
					List<DeliveryOrderDetailPurDto> dodpdList = new ArrayList<DeliveryOrderDetailPurDto>();
					for(Stock stock : stockList)
					{
						DeliveryOrderDetail dod = new DeliveryOrderDetail();
						DeliveryOrderDetailPurDto doddto = deliveryOrderDetailService.selectByPurId(stock);
						Optional.ofNullable(doddto).ifPresent(u->{
							// TODO: do something
							BigDecimal newweight = new BigDecimal(0);
							BeanCopier dodcopier = BeanCopier.create(DeliveryOrderDetailPurDto.class, DeliveryOrderDetail.class, false);
							dodcopier.copy(u, dod, null);
							dod.setActualweight(u.getActualweight().add(u.getBalance()));
							if(!StringUtils.isEmpty(u.getDeliveryno())&&!StringUtils.isEmpty(u.getSaledetailid()))
							{
								dodpdList.add(u);
							}
							if(!StringUtils.isEmpty(dod.getId()))
							{
								dodList.add(dod);
							};
						});


					}
					if(dodList.size()>0)
					{
						deliveryOrderDetailService.updateBatchById(dodList);
					}

                    if(dodpdList.size()>0)
					{
						List<DeliveryOrderDetailPurDto> dlolist = new ArrayList<DeliveryOrderDetailPurDto>();
						List<SaleContractDetailDto> scddList = new ArrayList<SaleContractDetailDto>();
						/** 根据采购入库前的入库吨位减去新的库存吨位的值，按照deliveryno做分类统计*/
						Map<String, List> deliveryOrderDetailPurDtoSum = dodpdList.stream()
								.collect(Collectors.groupingBy(DeliveryOrderDetailPurDto::getDeliveryno, Collectors.collectingAndThen(Collectors.toList(), m -> {
									/** 采购重量统计*/
									final BigDecimal balance = m
											.stream()
											.collect(CollectorsUtil.summingBigDecimal(DeliveryOrderDetailPurDto::getBalance));
									return Arrays.asList(
											balance
									);
								})));
						deliveryOrderDetailPurDtoSum.forEach((k,v) ->
						{
							DeliveryOrderDetailPurDto dlo = new DeliveryOrderDetailPurDto();
							dlo.setDeliveryno(k);
							dlo.setBalance(new BigDecimal(v.get(0).toString()));
							dlolist.add(dlo);
						});
						deliveryOrderService.batchUpdateBalance(dlolist);

						/** 根据采购入库前的入库吨位减去新的库存吨位的值，按照saledetailid做分类统计*/
						Map<Integer, List> saleOrderDetailDtoSum = dodpdList.stream()
								.collect(Collectors.groupingBy(DeliveryOrderDetailPurDto::getSaledetailid, Collectors.collectingAndThen(Collectors.toList(), m -> {
									/** 采购重量统计*/
									final BigDecimal balance = m
											.stream()
											.collect(CollectorsUtil.summingBigDecimal(DeliveryOrderDetailPurDto::getBalance));
									return Arrays.asList(
											balance
									);
								})));
						saleOrderDetailDtoSum.forEach((k,v) ->
						{
							SaleContractDetailDto sdd = new SaleContractDetailDto();
							sdd.setId(k);
							sdd.setBalance(new BigDecimal(v.get(0).toString()));
							scddList.add(sdd);
						});
						saleContractDetailService.batchUpdateBalance(scddList);
					}

					purchaseContractDetailService.updateBatchById(collection);
				}
				status = "更新成功";
			}
		 return ResultUtil.result(EnumCode.OK.getValue(), status);
	}

	@Override
	public Integer delBatchIds(List<String> asList, List<String> contractnos) {
		// TODO Auto-generated method stub
		Integer result = 0;
		for(String i: asList)
		{
			
			PurchaseContract purchaseContract = super.baseMapper.selectById(i);
			if("已审核".equals(purchaseContract.getPurchasestatus()))
			{
				return result;
			}
		}
		purchaseContractDetailService.deleteBatchNos(contractnos);
		result = super.baseMapper.deleteBatchIds(asList);
		return result;
	}

	@Override
	public Integer updateByVerify(Integer id, String purchaseno,String memberId) throws ParseException {
		// TODO Auto-generated method stub
	   	PurchaseContract purchaseContract = new PurchaseContract();
    	purchaseContract.setId(id);
    	purchaseContract.setPurchasestatus("已审核");
    	 UserInfoDto userInfoDto = new UserInfoDto();
    	 Subject subject = SecurityUtils.getSubject();   
    	 userInfoDto = (UserInfoDto) subject.getPrincipal();
    	 purchaseContract.setVerifyby(userInfoDto.getUsername());
    	 PurchaseContractDetail purchaseContractDetail = new PurchaseContractDetail();
    	 purchaseContractDetail.setPurchaseno(purchaseno);
//    	 purchaseContractDetail.setStatus("在库");
    	 EntityWrapper<PurchaseContractDetail> eWrapper = new EntityWrapper<PurchaseContractDetail>(purchaseContractDetail);
    	 List<PurchaseContractDetail> purchaseContractDetailList = purchaseContractDetailService.selectList(eWrapper);
    	 List<Stock> stockList = new ArrayList<Stock>();
    	 for(PurchaseContractDetail lpurchaseContractDetail:purchaseContractDetailList)
    	 {
    		 Stock stock = new Stock();
    		 BeanCopier copier = BeanCopier.create(PurchaseContractDetail.class, Stock.class, false);
    		 copier.copy(lpurchaseContractDetail, stock, null);
    		 String uuid = UUID.randomUUID().toString().replaceAll("-","");
    		 stock.setPurdetailid(lpurchaseContractDetail.getId());
    		 stock.setProductid(uuid);
    		 stock.setMemberid(memberId);
    		 stock.setStatus("在库");
			 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		 stock.setUpt(sdf1.parse(sdf1.format(new Date())));
    		 stockList.add(stock);
    	 }
    	 stockService.insertBatch(stockList);
    	 purchaseContractDetail.setStatus("在库");
    	 purchaseContractDetailService.updateByDetail(purchaseContractDetail);
    	 Integer status = super.baseMapper.updateById(purchaseContract);
		return status;
    	 
	}


	@Override
	public Boolean copyContract(String contractno) {
		// TODO Auto-generated method stub
		 PurchaseContract purchaseContract = new PurchaseContract();
		 purchaseContract.setPurchaseno(contractno);
		 purchaseContract = super.baseMapper.selectOne(purchaseContract);
	   	 String newpurchaseno = preName+CommonUtil.getTimeStamp();
	   	 purchaseContract.setPurchaseno(newpurchaseno);
	   	 purchaseContract.setPurchasestatus("待审核");
	   	 purchaseContract.setCrt(new Date());
	   	 super.baseMapper.insert(purchaseContract);
	   	 PurchaseContractDetail purchaseContractDetail = new PurchaseContractDetail();
	   	 purchaseContractDetail.setPurchaseno(contractno);
	   	 EntityWrapper<PurchaseContractDetail> eWrapper = new EntityWrapper<PurchaseContractDetail>(purchaseContractDetail);
	   	 List<PurchaseContractDetail> purchaseContractDetailList= purchaseContractDetailService.selectList(eWrapper);
	   	 if(purchaseContractDetailList.size()>0)
	   	 {
	   		 for(PurchaseContractDetail newpurchaseContractDetail:purchaseContractDetailList)
	   		 {
	   			newpurchaseContractDetail.setPurchaseno(newpurchaseno);
//	   			newpurchaseContractDetail.setProcessstatus(null);
//	   			newpurchaseContractDetail.setDeliverystatus(null);
	   			newpurchaseContractDetail.setStatus(null);
	   			newpurchaseContractDetail.setCrt(new Date());
	   		 }
	   		Boolean status =  purchaseContractDetailService.insertBatch(purchaseContractDetailList);
	   		 if (status)
	   	     {
	   	    	 return true;
	   	     }else
	   	     {
	   	    	 return false;
	   	     }
	   	 }else
	   	 {
	   	    	
	   			return false;
	   	 }

	}


}
