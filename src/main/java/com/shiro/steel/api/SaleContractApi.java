package com.shiro.steel.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.shiro.steel.pojo.constant.CommonConstant;
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
import com.shiro.steel.entity.CorpInfo;
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.entity.Product;
import com.shiro.steel.entity.Productfactory;
import com.shiro.steel.entity.Productmark;
import com.shiro.steel.entity.Productspec;
import com.shiro.steel.entity.PurchaseContract;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.entity.SaleContractWarehouse;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractAnalyseDto;
import com.shiro.steel.pojo.dto.SaleContractDetailDto;
import com.shiro.steel.pojo.dto.SaleContractDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.ContractVo;
import com.shiro.steel.service.CorpInfoService;
import com.shiro.steel.service.CustomerInfoService;
import com.shiro.steel.service.ProductFactoryService;
import com.shiro.steel.service.ProductMarkService;
import com.shiro.steel.service.ProductService;
import com.shiro.steel.service.ProductSpecService;
import com.shiro.steel.service.SaleContractAnalyseService;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.service.SaleContractService;
import com.shiro.steel.service.SaleContractWarehouseService;
import com.shiro.steel.utils.CnUpperCaser;
import com.shiro.steel.utils.ResultUtil;

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
	private SaleContractWarehouseService saleContractWarehouseService;
	
	@Autowired
	private SaleContractAnalyseService saleContractAnalyseService;
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
        SaleContractWarehouse saleContractWarehouse = new SaleContractWarehouse();
    	saleContractWarehouse.setMemberid(memberId);
        EntityWrapper<SaleContractWarehouse> saleContractWarehouseWrapper = new EntityWrapper<SaleContractWarehouse>(saleContractWarehouse);
        List<SaleContractWarehouse> saleContractWarehouseList = saleContractWarehouseService.selectList(saleContractWarehouseWrapper);


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
    	Boolean status = saleContractWarehouseService.deleteById(Integer.parseInt(id));
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
    	SaleContractWarehouse saleContractWarehouse = new SaleContractWarehouse();
    	saleContractWarehouse.setMemberid(memberId);
        EntityWrapper<SaleContractWarehouse> saleContractWarehouseWrapper = new EntityWrapper<SaleContractWarehouse>(saleContractWarehouse);
        List<SaleContractWarehouse> saleContractWarehouseList = saleContractWarehouseService.selectList(saleContractWarehouseWrapper);
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
//        	startTimeDate = new Date(startTime);  
//        	
//        	endTimeDate = new Date(endTime);
        }
        List<SaleContractAnalyseDto> saleContractAnalyseDtoList = saleContractAnalyseService.selectList(page,dto,createby,startTimeString,endTimeString);
        /** 毛利统计*/
        Map<String, List> saleContractAnalyseDtoSum = saleContractAnalyseDtoList.stream()
                .filter(t -> t.getActualweight() != null)
                .filter(t -> t.getFinalweight() != null)
                .collect(Collectors.groupingBy(SaleContractAnalyseDto::getContractno, Collectors.collectingAndThen(Collectors.toList(), m -> {
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
                    /** 客户名称*/
                    final String  customer = m
                            .stream()
                            .map(SaleContractAnalyseDto::getCustomername).distinct().collect(Collectors.joining(""));
                    /** 出库费*/
                    final BigDecimal stockOutTotalFee = m.stream().filter(t -> t.getStockoutfee() != null).reduce(BigDecimal.ZERO, (x, y) -> {
                        return x.add(y.getActualweight().multiply(y.getStockoutfee()==null?new BigDecimal(0.000):y.getStockoutfee()).setScale(3,BigDecimal.ROUND_HALF_UP));
                    }, BigDecimal::add);
                    /** 加工费*/
                    final BigDecimal processTotalFee = m.stream().filter(t -> t.getProcessfee() != null).reduce(BigDecimal.ZERO, (x, y) -> {
                        return x.add(y.getActualweight().multiply(y.getProcessfee()==null?new BigDecimal(0.000):y.getProcessfee()).setScale(3,BigDecimal.ROUND_HALF_UP));
                    }, BigDecimal::add);
                    /** 运输费*/
                    final String transportFee = m
                            .stream()
                            .map(n->String.valueOf(n.getTransportfee())).distinct().collect(Collectors.joining(""));
                    return Arrays.asList(customer,
                            actualWeight,
                            actualAmount,
                            finalWeight,
                            finalAmount,
                            processTotalFee==null?0.000:processTotalFee,
                            transportFee==null?0.000:transportFee,
                            stockOutTotalFee==null?0.000:stockOutTotalFee
                    );
                })));
        List<SaleContractAnalyseDto> analyseDtoList = new ArrayList<SaleContractAnalyseDto>();
        saleContractAnalyseDtoSum.forEach((k,v) ->
                {
                    System.out.println("key: " + k + " , " + "value: " + v);
                    SaleContractAnalyseDto saleContractAnalyseDto = new SaleContractAnalyseDto();
                    saleContractAnalyseDto.setContractno(k);
                    saleContractAnalyseDto.setCustomername(v.get(0).toString());
                    saleContractAnalyseDto.setActualweight(new BigDecimal(v.get(1).toString()));
                    saleContractAnalyseDto.setActualamount(new BigDecimal(v.get(2).toString()));
                    saleContractAnalyseDto.setFinalweight(new BigDecimal(v.get(3).toString()));
                    saleContractAnalyseDto.setFinalamount(new BigDecimal(v.get(4).toString()));
                    saleContractAnalyseDto.setProcessfee(new BigDecimal(v.get(5).toString()));
                    saleContractAnalyseDto.setTransportfee(new BigDecimal(v.get(6).toString()));
                    saleContractAnalyseDto.setStockouttotalfee(new BigDecimal(v.get(7).toString()));
                    BigDecimal grossprofit =
                            new BigDecimal(v.get(4).toString())
                                    .subtract(new BigDecimal(v.get(2).toString()))
                                        .subtract(new BigDecimal(v.get(5).toString()))
                                                .subtract(new BigDecimal(v.get(6).toString()))
                                                        .subtract(new BigDecimal(v.get(7).toString()));
                    saleContractAnalyseDto.setGrossprofit(grossprofit);
                    analyseDtoList.add(saleContractAnalyseDto);
                }
        );
        System.out.println(analyseDtoList.toString());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("saleContractAnalyseDtoList", analyseDtoList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",result);
    	
    }
}
