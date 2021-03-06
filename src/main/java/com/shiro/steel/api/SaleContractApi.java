package com.shiro.steel.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.shiro.steel.entity.*;
import com.shiro.steel.pojo.constant.CommonConstant;
import com.shiro.steel.pojo.dto.*;
import com.shiro.steel.service.*;
import com.shiro.steel.utils.CollectorsUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.api.base.BaseApi;
import com.shiro.steel.pojo.vo.ContractVo;
import com.shiro.steel.utils.CnUpperCaser;
import com.shiro.steel.utils.ResultUtil;
import sun.rmi.server.InactiveGroupException;

/**
 * @desc: 用户接口
 *
 * @author: jwy
 * @date: 2017/12/15
 */
@RestController
@RequestMapping(value = "SaleContractApi/v1")
public class SaleContractApi extends BaseApi {

    @Autowired
    private SaleContractService saleContractService;
    
    @Autowired
    private SaleContractDetailService saleContractDetailService;

    @Autowired
    private CorpInfoService  corpInfoService;
    
    @Autowired
    private CustomerInfoService  customerInfoService;
    
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
	private SaleContractAnalyseService saleContractAnalyseService;

	@Autowired
    private TransportOrderService transportOrderService;
       /**
     * @desc: 查询订单
     *
     * @param dto 参数dto
     * @author: jwy
     * @throws ParseException 
     * @date: 2017/12/19
     */
    @RequestMapping(value = "/findSaleContractByPage",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.POST})//跨域
    public Object findUserByPage(ParamsDto dto,@RequestParam(value="searchDate" ,required =false)Integer searchDate,String statusTab,String contracttype,String invoiceStatus,
    		String startTime,String endTime, String verifyBy ) throws ParseException{
    	UserInfoDto userInfoDto = new UserInfoDto();
    	Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
    	String createby = null;
    	Integer type = userInfoDto.getType();
    	if(type==1)
    	{
    		
    	}
    	else if(type==2)
    	{
    		createby = userInfoDto.getUsername();
    	}
        Page<SaleContractDto> page = new Page<>(dto.getStartPage(),dto.getPageSize());
        if("全部".equals(statusTab))
        {
        	statusTab = null;
        }
        Date startTimeDate = null;
        Date endTimeDate = null;
        String startTimeString = "";
        String endTimeString = "";		
        if(!StringUtils.isEmpty(startTime))
        {
        	 SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z",Locale.ENGLISH);
             startTimeDate = sf.parse(startTime);
             endTimeDate = sf.parse(endTime);
             startTimeString =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(startTimeDate);
             endTimeString =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(endTimeDate);
//        	startTimeDate = new Date(startTime);  
//        	
//        	endTimeDate = new Date(endTime);
        }
      
        List<SaleContractDto> list = saleContractService.findSaleContractByPage(page,dto,searchDate,statusTab,createby,contracttype,invoiceStatus,startTimeString,endTimeString,verifyBy);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list, page.getTotal(),page.getPages());
    }
    /**
     * @desc: 新增合同
     *
     * @author: jwy
     * @date: 2017/12/25
     */
    @RequestMapping(value = "/addConstract",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addUser(@Valid ContractVo contractVo, BindingResult bindingResult) {


       return saleContractService.addContract(contractVo);
    }
    
    /**
     * @desc: 查询订单明细
     *
     * @param dto 参数dto
     * @author: jwy
     * @throws Exception 
     * @date: 2017/12/19
     */
    @RequestMapping(value = "/findSaleContractDetailByPage",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findSaleContractDetailByPage(ParamsDto dto,String memberId,String customerId) throws Exception {
    	String key = dto.getKeyword();
        Map<String, Object> result = new HashMap<String, Object>();
    	Map<String, Object> selectMap = new HashMap<String, Object>();
    	selectMap.put("contractNo", key);
    	//获取订单主要信息
    	List<SaleContract> saleContractDto = saleContractService.selectByMap(selectMap);
        String digitUppercase = CnUpperCaser.digitUppercase(saleContractDto.get(0).getContractamount().toString());
        saleContractDto.get(0).setDigitUppercase(digitUppercase);
        //获取订单商品明细
        List<SaleContractDetailDto> list = saleContractDetailService.findSaleContractDetailByPage(dto);
        //获取产品名列表
        Product product = new Product();
        product.setMemberid(memberId);
        EntityWrapper<Product> productWrapper = new EntityWrapper<Product>(product);
        List<Product> productList = productService.selectList(productWrapper);
        
        //获取产品厂家列表
        Productfactory productfactory = new Productfactory();
        productfactory.setMemberid(memberId);
        EntityWrapper<Productfactory> productfactoryWrapper = new EntityWrapper<Productfactory>(productfactory);
        List<Productfactory> productfactoryList = productFactoryService.selectList(productfactoryWrapper);
        
        //获取产品牌号列表
        Productmark productmark = new Productmark();
        productmark.setMemberid(memberId);
        EntityWrapper<Productmark> productmarkWrapper = new EntityWrapper<Productmark>(productmark);
        List<Productmark> productmarkList = productMarkService.selectList(productmarkWrapper);
        
        //获取产品牌号列表
        Productspec productspec = new Productspec();
        productspec.setMemberid(memberId);
        EntityWrapper<Productspec> productspecWrapper = new EntityWrapper<Productspec>(productspec);
        List<Productspec> productspecList = productSpecService.selectList(productspecWrapper);
        
        //获取
        WarehouseInfo saleContractWarehouse = new WarehouseInfo();
    	saleContractWarehouse.setMemberid(memberId);
        EntityWrapper<WarehouseInfo> saleContractWarehouseWrapper = new EntityWrapper<WarehouseInfo>(saleContractWarehouse);
        List<WarehouseInfo> saleContractWarehouseList = warehouseInfoService.selectList(saleContractWarehouseWrapper);


        //获取供方企业信息
	    Map<String, Object> corpMap = new HashMap<String, Object>();
	 	corpMap.put("memberId",memberId);
	 	List<CorpInfo> corpInfo = corpInfoService.selectByMap(corpMap);
	 	//获取需方企业信息
     	CustomerInfo customerInfo = new CustomerInfo();
     	customerInfo.setMemberid(memberId);
     	customerInfo.setId(Integer.parseInt(customerId));
	   	EntityWrapper<CustomerInfo> eWrapper = new EntityWrapper<CustomerInfo>(customerInfo);
	    CustomerInfo  customer = customerInfoService.selectOne(eWrapper);
	    //合并结果集
        result.put("saleContract", saleContractDto.get(0));
        result.put("productList", productList);
        result.put("productfactoryList", productfactoryList);
        result.put("productmarkList", productmarkList);
        result.put("productspecList", productspecList);
        result.put("saleContractDetail", list);
        result.put("saleContractWarehouseList", saleContractWarehouseList);
        result.put("corpInfo", corpInfo.get(0));	
        result.put("customer", customer);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",result );
    }
    /**
     * @desc: 删除合同
     *
     * @author: jwy
     * @date: 2017/12/25
     */
    @RequestMapping(value = "/delConstracts",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delConstracts(String[] ids,String[] contractnos) {
     Integer r =  saleContractService.delBatchIds(Arrays.asList(ids),Arrays.asList(contractnos));
     if (r==600)
     {
    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "关联加工单已存在，删除失败，如需删除，请先删除对应的加工单！");
     }else if(r==800)
     {
    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "关联提单已存在，删除失败，如需删除，请先删除对应的提单！");
     }
     else if(r>0)
     {
    	 return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
     }else
     {
    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "删除失败");
     }
       
     
    }
    
    @RequestMapping(value = "/verifySaleContract" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object verifySaleContract(Integer id,String contractno,String contractType){
    	 SaleContract saleContract = new SaleContract();
    	 saleContract.setId(id);
    	 if(CommonConstant.UNAUDIT_TEMP_GOODS_CONTRACT.equals(contractType))
    	 {
    		 saleContract.setContractstatus(CommonConstant.AUDIT_TEMP_GOODS_CONTRACt);
    	 }else if(CommonConstant.UNAUDIT_TEMP_CONTRACT.equals(contractType))
    	 {
    		 saleContract.setContractstatus(CommonConstant.AUDIT_TEMP_CONTRACT);
    	 }
    	 UserInfoDto userInfoDto = new UserInfoDto();
    	 Subject subject = SecurityUtils.getSubject();   
    	 userInfoDto = (UserInfoDto) subject.getPrincipal();
    	 saleContract.setVerifyBy(userInfoDto.getUsername());
    	 Boolean status = saleContractService.update(saleContract,contractno);
//    	 Boolean status = saleContractService.updateById(saleContract);
    	 if (status)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "审核成功");
	     }else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "审核失败");
	     }
    }
    
    
    @RequestMapping(value = "/confirmSaleContract" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object confirmSaleContract(Integer id,String isornot){
    	SaleContract saleContract = new SaleContract();
    	String msg = null;
		 saleContract.setId(id);
		 if("0".equals(isornot))
         {
             saleContract.setInvoicestatus("未开");
             msg= "撤回成功";
         }
		 else if("1".equals(isornot))
         {
             saleContract.setInvoicestatus("已开");
             msg= "确认已开";
         }

    	 Boolean status = saleContractService.updateById(saleContract);
    	 if (status)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), msg);
	     }else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "确认失败");
	     }
    }
    
    @RequestMapping(value = "/copyContract" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object copyContract(String contractno){
    	 Boolean status = saleContractService.copyContract(contractno);
    	 if (status)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "复制成功");
	     }else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "复制失败");
	     }
    }
    
    @RequestMapping(value = "/delProduct" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delProduct(String id)
    {
    	Boolean status = productService.deleteById(Integer.parseInt(id));
    	if(status)
    	{
        	return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    	}
    	else
    	{
    		return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "删除失败");
    	}
    }
    
    @RequestMapping(value = "/delProductFactory" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delProductFactory(String id)
    {
    	Boolean status = productFactoryService.deleteById(Integer.parseInt(id));
    	if(status)
    	{
        	return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    	}
    	else
    	{
    		return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "删除失败");
    	}
    }
    
    @RequestMapping(value = "/delProductMark" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delProductMark(String id)
    {
    	Boolean status = productMarkService.deleteById(Integer.parseInt(id));
    	if(status)
    	{
        	return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    	}
    	else
    	{
    		return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "删除失败");
    	}
    }
    
    @RequestMapping(value = "/delProductSpec" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delProductSpec(String id)
    {
    	Boolean status = productSpecService.deleteById(Integer.parseInt(id));
    	if(status)
    	{
        	return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    	}
    	else
    	{
    		return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "删除失败");
    	}
    }
    
    @RequestMapping(value = "/delSaleContractWarehouse" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delSaleContractWarehouse(String id,String memberId)
    {
    	Boolean status = warehouseInfoService.deleteById(Integer.parseInt(id));
    	if(status)
    	{
        	return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    	}
    	else
    	{
    		return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "删除失败");
    	}
    }
    
    
    
    @RequestMapping(value = "/findProduct" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findProduct(String memberId)
    {
    	Product product = new Product();
        product.setMemberid(memberId);
        EntityWrapper<Product> productWrapper = new EntityWrapper<Product>(product);
        List<Product> productList = productService.selectList(productWrapper);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("productList", productList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",result);
    	
    }
    
    
    @RequestMapping(value = "/findProductfactory" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findProductfactory(String memberId)
    {
    	Productfactory productfactory = new Productfactory();
        productfactory.setMemberid(memberId);
        EntityWrapper<Productfactory> productfactoryWrapper = new EntityWrapper<Productfactory>(productfactory);
        List<Productfactory> productfactoryList = productFactoryService.selectList(productfactoryWrapper);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("productfactoryList", productfactoryList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",result);
    	
    }
    
    @RequestMapping(value = "/findProductmark" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findProductmark(String memberId)
    {
        //获取产品牌号列表
        Productmark productmark = new Productmark();
        productmark.setMemberid(memberId);
        EntityWrapper<Productmark> productmarkWrapper = new EntityWrapper<Productmark>(productmark);
        List<Productmark> productmarkList = productMarkService.selectList(productmarkWrapper);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("productmarkList", productmarkList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",result);
    	
    }
    
    @RequestMapping(value = "/findProductSpec" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findProductSpec(String memberId)
    {
    	Productspec productspec = new Productspec();
        productspec.setMemberid(memberId);
        EntityWrapper<Productspec> productspecWrapper = new EntityWrapper<Productspec>(productspec);
        List<Productspec> productspecList = productSpecService.selectList(productspecWrapper);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("productspecList", productspecList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",result);
    	
    }
    
    @RequestMapping(value = "/findSaleContractWarehouse" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findSaleContractWarehouse(String memberId)
    {
        WarehouseInfo warehouse_info = new WarehouseInfo();
        warehouse_info.setMemberid(memberId);
        EntityWrapper<WarehouseInfo> saleContractWarehouseWrapper = new EntityWrapper<WarehouseInfo>(warehouse_info);
        List<WarehouseInfo> saleContractWarehouseList = warehouseInfoService.selectList(saleContractWarehouseWrapper);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("saleContractWarehouseList", saleContractWarehouseList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",result);
    	
    }
    
    
    @RequestMapping(value = "/findSaleContractAnalyse" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findSaleContractAnalyse(ParamsDto dto,String memberId,String startTime,String endTime) throws ParseException
    {
    	UserInfoDto userInfoDto = new UserInfoDto();
    	Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
    	String createby = null;
    	Integer type = userInfoDto.getType();
    	if(type==1)
    	{
    		
    	}
    	else if(type==2)
    	{
    		createby = userInfoDto.getUsername();
    	}
        Page<SaleContractDto> page = new Page<>(dto.getStartPage(),dto.getPageSize());
        Date startTimeDate = null;
        Date endTimeDate = null;
        String startTimeString = "";
        String endTimeString = "";		
        if(!StringUtils.isEmpty(startTime))
        {
        	 SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z",Locale.ENGLISH);
             startTimeDate = sf.parse(startTime);
             endTimeDate = sf.parse(endTime);
             startTimeString =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(startTimeDate);
             endTimeString =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(endTimeDate);
        }
        List<TransportOrderDto> transportOrderDtoList = transportOrderService.selectListBytransport(page,dto,createby,startTimeString,endTimeString);
        List<SaleContractAnalyseDto> finalanalyseDtoList = new ArrayList<SaleContractAnalyseDto>();
        for(TransportOrderDto tod:transportOrderDtoList)
        {
            String[] deliverynos = tod.getDeliveryno().split(",");
            List<SaleContractAnalyseDto> analyseDtoList = new ArrayList<SaleContractAnalyseDto>();
            for(String deliveryno:deliverynos)
            {
                List<SaleContractAnalyseDto> saleContractAnalyseDtoList = saleContractAnalyseService.selectList(deliveryno);
                /** 毛利统计*/
                Map<String, List> saleContractAnalyseDtoSum = saleContractAnalyseDtoList.stream()
                .collect(Collectors.groupingBy(SaleContractAnalyseDto::getDeliveryno, Collectors.collectingAndThen(Collectors.toList(), m -> {
                    /** 采购重量统计*/
                    final BigDecimal actualWeight = m
                            .stream()
                            .collect(CollectorsUtil.summingBigDecimal(SaleContractAnalyseDto::getActualweight));
                    /** 采购的金额统计*/
                    final BigDecimal actualAmount = m.stream().reduce(BigDecimal.ZERO, (x, y) -> {
                        return x.add(y.getActualweight().multiply(y.getPurchaseprice()).setScale(3,BigDecimal.ROUND_HALF_UP));
                    }, BigDecimal::add);

                    /** 客户确认的重量统计*/
                    final BigDecimal finalWeight = m
                            .stream()
                            .collect(CollectorsUtil.summingBigDecimal(SaleContractAnalyseDto::getFinalweight));
                    /** 客户确认的金额统计*/
                    final BigDecimal finalAmount = m.stream().reduce(BigDecimal.ZERO, (x, y) -> {
                        return x.add(y.getFinalweight().multiply(y.getSaleprice()).setScale(3, BigDecimal.ROUND_HALF_UP));
                    }, BigDecimal::add);
                    /** 出库费*/
                    final String stockOutTotalFee = m
                            .stream()
                            .filter(t -> !StringUtils.isEmpty((t.getStockoutfee())))
                            .map(n->String.valueOf(n.getStockoutfee())).distinct().collect(Collectors.joining());
                    /** 加工费*/
                    final BigDecimal processTotalFee = m.stream().filter(t -> t.getProcessfee() != null).reduce(BigDecimal.ZERO, (x, y) -> {
                        return x.add(y.getActualweight().multiply(y.getProcessfee()==null?new BigDecimal(0.000):y.getProcessfee()).setScale(3,BigDecimal.ROUND_HALF_UP));
                    }, BigDecimal::add);
                    return Arrays.asList(
                            actualWeight,
                            actualAmount,
                            finalWeight,
                            finalAmount,
                            processTotalFee==null?0.000:processTotalFee,
                            stockOutTotalFee.equals("")?0.000:stockOutTotalFee
                    );
                })));

                saleContractAnalyseDtoSum.forEach((k,v) ->
                        {
                            System.out.println("key: " + k + " , " + "value: " + v);
                            SaleContractAnalyseDto saleContractAnalyseDto = new SaleContractAnalyseDto();
                            saleContractAnalyseDto.setDeliveryno(k);
                            saleContractAnalyseDto.setActualweight(new BigDecimal(v.get(0).toString()));
                            saleContractAnalyseDto.setActualamount(new BigDecimal(v.get(1).toString()));
                            saleContractAnalyseDto.setFinalweight(new BigDecimal(v.get(2).toString()));
                            saleContractAnalyseDto.setFinalamount(new BigDecimal(v.get(3).toString()));
                            saleContractAnalyseDto.setProcessfee(new BigDecimal(v.get(4).toString()));
                            saleContractAnalyseDto.setStockouttotalfee(new BigDecimal(v.get(5).toString()));
                            analyseDtoList.add(saleContractAnalyseDto);
                        }
                );
            }
            SaleContractAnalyseDto newsaleContractAnalyseDto = new SaleContractAnalyseDto();
            BigDecimal sumprocessTotalFee = new BigDecimal(0);
            BigDecimal sumstockOutTotalFee = new BigDecimal(0);
            BigDecimal totalactualweight= new BigDecimal(0);
            BigDecimal totalactualamount= new BigDecimal(0);
            BigDecimal totalfinalweight = new BigDecimal(0);
            BigDecimal totalfinalamount = new BigDecimal(0);
            for(SaleContractAnalyseDto saleContractAnalyseDto:analyseDtoList)
            {
                sumprocessTotalFee = sumprocessTotalFee.add(saleContractAnalyseDto.getProcessfee());
                sumstockOutTotalFee =sumstockOutTotalFee.add(saleContractAnalyseDto.getStockouttotalfee());
                totalactualweight = totalactualweight.add(saleContractAnalyseDto.getActualweight());
                totalactualamount = totalactualamount.add(saleContractAnalyseDto.getActualamount());
                totalfinalweight = totalfinalweight.add(saleContractAnalyseDto.getFinalweight());
                totalfinalamount = totalfinalamount.add(saleContractAnalyseDto.getFinalamount());
            }
            newsaleContractAnalyseDto.setContractno(tod.getContractno());
            newsaleContractAnalyseDto.setTransportno(tod.getTransportno());
            newsaleContractAnalyseDto.setCustomername(tod.getCustomername());
            newsaleContractAnalyseDto.setActualweight(totalactualweight);
            newsaleContractAnalyseDto.setActualamount(totalactualamount);
            newsaleContractAnalyseDto.setFinalweight(totalfinalweight);
            newsaleContractAnalyseDto.setFinalamount(totalfinalamount);
            newsaleContractAnalyseDto.setTransportfee(tod.getTransportfee());
            newsaleContractAnalyseDto.setProcessfee(sumprocessTotalFee);
            newsaleContractAnalyseDto.setStockouttotalfee(sumstockOutTotalFee);
            BigDecimal grossprofit =
                            totalfinalamount
                                    .subtract(totalactualamount)
                                        .subtract(tod.getTransportfee())
                                                .subtract(sumprocessTotalFee)
                                                        .subtract(sumstockOutTotalFee);
            newsaleContractAnalyseDto.setGrossprofit(grossprofit);
            finalanalyseDtoList.add(newsaleContractAnalyseDto);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("saleContractAnalyseDtoList", finalanalyseDtoList);
//        result.put("saleContractAnalyseDtoList", analyseDtoList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",result);
    	
    }
}
