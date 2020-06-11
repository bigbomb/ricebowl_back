package com.shiro.steel.service.ipml;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shiro.steel.entity.*;
import com.shiro.steel.pojo.constant.CommonConstant;
import com.shiro.steel.service.*;
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
import com.shiro.steel.mapper.CustomerInfoMapper;
import com.shiro.steel.mapper.SaleContractDetailMapper;
import com.shiro.steel.mapper.SaleContractMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.ContractVo;
import com.shiro.steel.pojo.vo.CustomerInfoVo;
import com.shiro.steel.utils.CommonUtil;
import com.shiro.steel.utils.ResultUtil;
@Service
public class SaleContractServiceImpl extends ServiceImpl<SaleContractMapper, SaleContract> implements SaleContractService{

	@Autowired
	private SaleContractService saleContractService;
	
	@Autowired 
	private SaleContractDetailService saleContractDetailService;
	
	@Autowired
	private ProcessOrderService processOrderService;
	
	@Autowired
	private DeliveryOrderService deliveryOrderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductFactoryService productFactoryService;
	
	@Autowired
	private ProductMarkService productMarkService;
	
	@Autowired
	private ProductSpecService productSpecService;
	
	@Autowired
	private WarehouseInfoService warehouseInfoService;
    
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private StockService stockService;
    final static String preName = "ext";
	@Override
	public List<SaleContractDto> findSaleContractByPage(Page<SaleContractDto> page, ParamsDto dto,Integer searchDate) {
		// TODO Auto-generated method stub
		List<SaleContractDto> list = super.baseMapper.findSaleContractByPage(page,dto,searchDate);
        return list;
	}

	@Override
	public Object addContract(ContractVo contractVo) {
		// TODO Auto-generated method stub
    	String contractno = "";
    	//contractVo.setContractstatus(ContractStatus.TOPAY.getText());
    	String saleContractDetail = contractVo.getSaleContractDetail();
    	List<SaleContractDetail>  collection = JSONObject.parseArray(saleContractDetail, SaleContractDetail.class);
    	String status ="";
		SaleContract saleContract = new SaleContract();
		BeanCopier copier = BeanCopier.create(ContractVo.class, SaleContract.class, false);
		copier.copy(contractVo, saleContract, null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		UserInfoDto userInfoDto = new UserInfoDto();
        Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
		try {
			if(StringUtils.isEmpty(contractVo.getContractno()))
			{
				contractno = preName+CommonUtil.getTimeStamp();
				saleContract.setContractno(contractno);
				List<Product> productList = new ArrayList<Product>();
				List<Productfactory> productfactoryList = new ArrayList<Productfactory>();
				List<Productmark> productmarkList = new ArrayList<Productmark>();
				List<Productspec> productspecList = new ArrayList<Productspec>();
				List<WarehouseInfo> saleContractWarehouseList = new ArrayList<WarehouseInfo>();
				List<Stock> stockItemList = new ArrayList<Stock>();
				List checkProduct = new ArrayList();
		    	for(SaleContractDetail s:collection){
			    		if("1".equals(saleContract.getContracttype()))
						{
							
							s.setActualweight(s.getWeight());
						}
		    		  Product product = new Product();
					  Productfactory productfactory = new Productfactory();
					  Productmark productmark = new Productmark();
					  Productspec productspec = new Productspec();
					  WarehouseInfo saleContractWarehouse = new WarehouseInfo();
		  		   	  s.setContractno(contractno); 
	  				  s.setStatus("待审核");

		  		 	  EntityWrapper<Product> eWrapper = new EntityWrapper<Product>(product);
		  		 	  product.setProductname(s.getProductname());
		  		 	  product.setMemberid(contractVo.getMemberid());
		  		      product = productService.selectOne(eWrapper);
		  		      if(product == null)
		  		      {
		  		    	product = new Product();
		  		    	product.setProductname(s.getProductname());
			  		 	product.setMemberid(contractVo.getMemberid());
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
		  		      productfactory.setMemberid(contractVo.getMemberid());
		  		      productfactory = productFactoryService.selectOne(wapperFactory);
		  		      if(productfactory == null)
		  		      {
		  		    	productfactory = new Productfactory();
		  		    	productfactory.setFactoryname(s.getProductfactory());
		  		    	productfactory.setMemberid(contractVo.getMemberid());
		  		    	
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
	  		      	  productmark.setMemberid(contractVo.getMemberid());
	  		      	  productmark = productMarkService.selectOne(wrapperMark);
		  		      if(productmark == null)
		  		      {
		  		    	productmark = new Productmark();
		  		    	productmark.setMarkname(s.getProductmark());
		  		    	productmark.setMemberid(contractVo.getMemberid());
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
		  		      productspec.setMemberid(contractVo.getMemberid());
		  		      productspec = productSpecService.selectOne(wrapperSpec);
		  		      if(productspec == null)
		  		      {
		  		    	productspec = new Productspec();
		  		    	productspec.setSpecname(s.getProductspec());
		  		    	productspec.setMemberid(contractVo.getMemberid());
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
		  		      
		  		      EntityWrapper<WarehouseInfo> SaleContractWarehouseWapper = new EntityWrapper<WarehouseInfo>(saleContractWarehouse);
		  		      saleContractWarehouse.setWarehousename(s.getWarehousename());
		  		      saleContractWarehouse.setMemberid(contractVo.getMemberid());
		  		      saleContractWarehouse = warehouseInfoService.selectOne(SaleContractWarehouseWapper);
		  		      if(saleContractWarehouse == null)
		  		      {
		  		    	saleContractWarehouse = new WarehouseInfo();
		  		    	saleContractWarehouse.setWarehousename(s.getWarehousename());
			  		    saleContractWarehouse.setMemberid(contractVo.getMemberid());
		  		    	
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
		    	if(stockItemList.size()>0)
		    	{
		    		stockService.updateBatchById(stockItemList);
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
		    	if(productfactoryList.size()>0)
		    	{
		    		productSpecService.insertBatch(productspecList);
		    	}
		    	if(saleContractWarehouseList.size()>0)
		    	{
		    		warehouseInfoService.insertBatch(saleContractWarehouseList);
		    	}
		    	
			    String customerIdString ="";
		    	if(StringUtils.isEmpty(contractVo.getCustomerid()))
		    	{
		    		CustomerInfo customerInfo = new CustomerInfo();
		    		customerInfo.setCompanyname(contractVo.getCustomername());
		    		customerInfo.setMemberid(contractVo.getMemberid());
		    		customerInfo.setCrt(new Date());
		    		customerInfoMapper.getIdAfterInsert(customerInfo).toString();
		    		customerIdString =customerInfo.getId().toString();
		    	}
		    	else {
		    		customerIdString = contractVo.getCustomerid();
				}
		    	saleContract.setCustomerid(customerIdString);
				saleContract.setContractdate(sdf.parse(sdf.format(new Date())));
				saleContract.setCrt(sdf1.parse(sdf1.format(new Date())));
				if("0".equals(saleContract.getContracttype()))
				{
					
					saleContract.setContractstatus(CommonConstant.UNAUDIT_TEMP_CONTRACT);
				}
				else
				{
					saleContract.setContractstatus(CommonConstant.UNAUDIT_TEMP_GOODS_CONTRACT);
//					saleContract.setVerifyBy(userInfoDto.getUsername());
				}
				saleContract.setCreateBy(userInfoDto.getUsername());
				saleContract.setInvoicestatus("未开");
				super.baseMapper.insert(saleContract);
				saleContractDetailService.insertBatch(collection);
				status = "新增成功";
			}
			else
			{
				List<Product> productList = new ArrayList<Product>();
				List<Productfactory> productfactoryList = new ArrayList<Productfactory>();
				List<Productmark> productmarkList = new ArrayList<Productmark>();
				List<Productspec> productspecList = new ArrayList<Productspec>();
				List<WarehouseInfo> saleContractWarehouseList = new ArrayList<WarehouseInfo>();
				 saleContract.setUpt(sdf1.parse(sdf1.format(new Date())));
				 super.baseMapper.updateByPrimaryKey(saleContract);
				 Map contractMap = new HashMap();
				 contractMap.put("contractNo", saleContract.getContractno());
				 saleContractDetailService.deleteByMap(contractMap);
				 List checkProduct = new ArrayList();
				 for(SaleContractDetail s:collection){
					   Product product = new Product();
					   Productfactory productfactory = new Productfactory();
					   Productmark productmark = new Productmark();
					   Productspec productspec = new Productspec();
					   WarehouseInfo saleContractWarehouse = new WarehouseInfo();
			  		   s.setContractno(saleContract.getContractno()); 
				  		 if("意向临调合同".equals(contractVo.getContractstatus()))
			  			  {
		  				  	s.setStatus("待审核");
			  			  }
			  		   EntityWrapper<Product> eWrapper = new EntityWrapper<Product>(product);
	  		 	  	   product.setProductname(s.getProductname());
		  		 	   product.setMemberid(contractVo.getMemberid());
		  		       product = productService.selectOne(eWrapper);
		  		      if(product == null)
		  		      {
		  		    	product = new Product();
		  		    	product.setProductname(s.getProductname());
			  		 	product.setMemberid(contractVo.getMemberid());
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
		  		      productfactory.setMemberid(contractVo.getMemberid());
		  		      productfactory = productFactoryService.selectOne(wapperFactory);
		  		      if(productfactory == null)
		  		      {
		  		    	productfactory = new Productfactory();
		  		    	productfactory.setFactoryname(s.getProductfactory());
		  		    	productfactory.setMemberid(contractVo.getMemberid());
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
	  		      	  productmark.setMemberid(contractVo.getMemberid());
	  		      	  productmark = productMarkService.selectOne(wrapperMark);
		  		      if(productmark == null)
		  		      {
		  		    	productmark = new Productmark();
		  		    	productmark.setMarkname(s.getProductmark());
		  		    	productmark.setMemberid(contractVo.getMemberid());
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
		  		      productspec.setMemberid(contractVo.getMemberid());
		  		      productspec = productSpecService.selectOne(wrapperSpec);
		  		      if(productspec == null)
		  		      {
		  		    	productspec = new Productspec();
		  		    	productspec.setSpecname(s.getProductspec());
		  		    	productspec.setMemberid(contractVo.getMemberid());
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
		  		    EntityWrapper<WarehouseInfo> SaleContractWarehouseWapper = new EntityWrapper<WarehouseInfo>(saleContractWarehouse);
		  		      saleContractWarehouse.setWarehousename(s.getWarehousename());
		  		      saleContractWarehouse.setMemberid(contractVo.getMemberid());
		  		      saleContractWarehouse = warehouseInfoService.selectOne(SaleContractWarehouseWapper);
		  		      if(saleContractWarehouse == null)
		  		      {
		  		    	saleContractWarehouse = new WarehouseInfo();
		  		    	saleContractWarehouse.setWarehousename(s.getWarehousename());
			  		    saleContractWarehouse.setMemberid(contractVo.getMemberid());
		  		    	
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
			    		warehouseInfoService.insertBatch(saleContractWarehouseList);
			    	}
				 saleContractDetailService.insertBatch(collection);
				 status = "更新成功";
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		 return ResultUtil.result(EnumCode.OK.getValue(), status);
	}

	@Override
	public Integer delBatchIds(List<String> ids,List<String> contractnos) {
		List<Stock> stockList = new  ArrayList<Stock>();
		for(String contractno:contractnos)
		{
			ProcessOrder processOrder = new ProcessOrder();
			processOrder.setContractno(contractno);
			EntityWrapper<ProcessOrder> wrapper = new EntityWrapper<ProcessOrder>(processOrder);
			processOrder =  processOrderService.selectOne(wrapper);
			if(processOrder!=null)
			{
				return 600;
			}
			else
			{
				DeliveryOrder deliveryOrder = new DeliveryOrder();
				deliveryOrder.setContractno(contractno);
				EntityWrapper<DeliveryOrder> eWrapper = new EntityWrapper<DeliveryOrder>(deliveryOrder);
				deliveryOrder = deliveryOrderService.selectOne(eWrapper);
				if(deliveryOrder!=null)
				{
					return 800;
				}
			}
			SaleContract saleContract = new SaleContract();
			saleContract.setContractno(contractno);
			EntityWrapper<SaleContract> eWrapper = new EntityWrapper<SaleContract>(saleContract);
			saleContract = saleContractService.selectOne(eWrapper);
			String contractType = saleContract.getContracttype();
			if("1".equals(contractType))
			{
				SaleContractDetail saleContractDetail = new SaleContractDetail();
				saleContractDetail.setContractno(contractno);
				EntityWrapper<SaleContractDetail> wwrapper = new EntityWrapper<SaleContractDetail>(saleContractDetail);
				List<SaleContractDetail> saleContractDetailList=  saleContractDetailService.selectList(wwrapper);
//				for(SaleContractDetail newsaleContractDetail:saleContractDetailList)
//				{
//					Stock stock = new Stock();
//					Integer stockid = newsaleContractDetail.getStockid();
//					stock.setId(stockid);
//					stock.setStatus("在库");
//					stock.setLockman("");
//					stockList.add(stock);
//				}
			}
		}
//		if(stockList.size()>0)
//		{
//			stockService.updateBatchById(stockList);
//		}
		saleContractDetailService.deleteBatchNos(contractnos);
		Integer result = super.baseMapper.deleteBatchIds(ids);
		return result;
	}

	@Override
	public Boolean copyContract(String contractno) {
		// TODO Auto-generated method stub
		 SaleContract saleContract = new SaleContract();
    	 saleContract.setContractno(contractno);
    	 saleContract = super.baseMapper.selectOne(saleContract);
    	 String newcontractno = preName+CommonUtil.getTimeStamp();
    	 saleContract.setContractno(newcontractno);
    	 saleContract.setContractstatus("意向临调合同");
    	 saleContract.setContracttype("0");
    	 saleContract.setVerifyBy(null);
    	 saleContract.setActualamount(new BigDecimal(0));
    	 saleContract.setActualweight(new BigDecimal(0));
    	 saleContract.setUpt(null);
    	 saleContract.setCrt(new Date());
    	 super.baseMapper.insert(saleContract);
    	 SaleContractDetail saleContractDetail = new SaleContractDetail();
    	 saleContractDetail.setContractno(contractno);
    	 EntityWrapper<SaleContractDetail> eWrapper = new EntityWrapper<SaleContractDetail>(saleContractDetail);
    	 List<SaleContractDetail> saleContractDetailList= saleContractDetailService.selectList(eWrapper);
    	 if(saleContractDetailList.size()>0)
    	 {
    		 for(SaleContractDetail newsaleContractDetail:saleContractDetailList)
    		 {
    			 newsaleContractDetail.setContractno(newcontractno);
    			 newsaleContractDetail.setProcessstatus(null);
    			 newsaleContractDetail.setDeliverystatus(null);
    			 newsaleContractDetail.setStatus(null);
    			 newsaleContractDetail.setCrt(new Date());
    		 }
    		Boolean status =  saleContractDetailService.insertBatch(saleContractDetailList);
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

	@Override
	public Boolean updateByParam(SaleContract saleContract) {
		// TODO Auto-generated method stub
		return super.baseMapper.updateByParam(saleContract);
	}

	@Override
	public List<SaleContractDto> findSaleContractByPage(Page<SaleContractDto> page, ParamsDto dto, Integer searchDate,
			String statusTab,String createby,String contracttype,String invoiceStatus, String startTimeString, String endTimeString,String verifyBy) {
		// TODO Auto-generated method stub
		List<SaleContractDto> list = super.baseMapper.findSaleContractByStatusPage(page,dto,searchDate,statusTab,createby,contracttype,invoiceStatus,startTimeString,endTimeString,verifyBy);
        return list;
	}

	@Override
	public Boolean update(SaleContract saleContract,String contractno) {
		// TODO Auto-generated method stub
		Integer status = super.baseMapper.updateById(saleContract);
		SaleContractDetail saleContractDetail = new SaleContractDetail();
		saleContractDetail.setContractno(contractno);
		saleContractDetail.setStatus("已审核");
		saleContractDetail.setUpt(new Date());
		saleContractDetailService.updateByContractno(saleContractDetail);
		if (status>0)
	     {
	    	 return true;
	     }else
	     {
	    	 return false;
	     }
	}

	@Override
	public Boolean updateByContract(SaleContract saleContract) {
		// TODO Auto-generated method stub
		return super.baseMapper.updateByContract(saleContract);
	}

	@Override
	public Integer batchWeigtAmountUpdate(List<SaleContractDto> finaList) {
		// TODO Auto-generated method stub
		return super.baseMapper.batchWeigtAmountUpdate(finaList);
		
	}

	


}
