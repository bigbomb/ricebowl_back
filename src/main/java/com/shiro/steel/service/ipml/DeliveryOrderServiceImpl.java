package com.shiro.steel.service.ipml;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.entity.DeliveryOrder;
import com.shiro.steel.entity.DeliveryOrderDetail;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.entity.Stock;
import com.shiro.steel.entity.WarehouseInfo;
import com.shiro.steel.mapper.DeliveryOrderMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.DeliveryOrderDetailVo;
import com.shiro.steel.pojo.vo.DeliveryOrderVo;
import com.shiro.steel.pojo.vo.ProcessOrderVo;
import com.shiro.steel.service.DeliveryOrderDetailService;
import com.shiro.steel.service.DeliveryOrderService;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.service.SaleContractService;
import com.shiro.steel.service.StockService;
import com.shiro.steel.service.WarehouseInfoService;import com.shiro.steel.utils.GeneratorUtil;

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
	private SaleContractService saleContractService;
	
	@Autowired
	private SaleContractDetailService  saleContractDetailService;
	
	@Autowired
	private DeliveryOrderDetailService deliveryOrderDetailService;
	
	@Autowired
	private StockService stockService;
	@Override
	public Boolean addDeliveryOrder(DeliveryOrderVo deliveryOrderVo) {
		// TODO Auto-generated method stub
		Boolean isstock = false;
		String deliveryno = "";
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
 	    List<SaleContractDetail>  collection = JSONObject.parseArray(saleContractDetail, SaleContractDetail.class);
 	    BigDecimal totalAmount = new BigDecimal(0);
 	    BigDecimal totalWeight = new BigDecimal(0);
 	    List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
 	    List<Stock> stockList = new ArrayList<Stock>();
 	    for(SaleContractDetail s:collection){
 	      Stock stock = new Stock();	
 	      stock.setId(s.getStockid());
 	      stock.setStatus("已出库");
 		  BigDecimal amount = new BigDecimal(0);
  		  amount = s.getFinalweight().multiply(s.getPrice()).multiply(new BigDecimal(s.getNum())).setScale(3,BigDecimal.ROUND_HALF_UP);
  		  totalWeight = totalWeight.add(s.getFinalweight());
  		  totalAmount = totalAmount.add(amount);
  		  SaleContractDetail  newsaleContractDetail = new SaleContractDetail();
  		  newsaleContractDetail.setActualweight(s.getActualweight());
  		  newsaleContractDetail.setFinalweight(s.getFinalweight());
  		  newsaleContractDetail.setId(s.getId());
  		  newsaleContractDetail.setDeliverystatus("提货中");
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
 	    deliveryOrderDetailService.insertBatch(deliveryOrderDetailList);
 	    saleContractDetailService.insertOrUpdateBatch(saleContractDetailList);
 	    SaleContract saleContract = new SaleContract();
 	    saleContract.setContractno(deliveryOrderVo.getContractno());
 	    saleContract.setActualamount(totalAmount);
 	    saleContract.setActualweight(totalWeight);
// 	    if(isstock)
// 	    {
// 	 	    saleContract.setContractstatus("现货合同");
// 	    }
// 	    else
// 	    {
// 	 	    saleContract.setContractstatus("正式临调合同");
// 	    }

 	    Boolean status = saleContractService.updateByContract(saleContract);
		return status;
	}

	@Override
	public List<DeliveryOrderVo> findByPage(Page<DeliveryOrderVo> page, ParamsDto dto, String memberId,String createby,String startTime,String endTime) {
		// TODO Auto-generated method stub
		List<DeliveryOrderVo> list = super.baseMapper.findByPage(page,dto,memberId,createby,startTime,endTime);
        return list;
	}

	@Override
	public List<DeliveryOrderDetailVo> findDetailByPageList( ParamsDto dto,
			String memberId, String deliveryNo) {
		// TODO Auto-generated method stub
		List<DeliveryOrderDetailVo> list = deliveryOrderDetailService.findDetailByPageList(dto,memberId,deliveryNo);
        return list;
	}
	

    
}
