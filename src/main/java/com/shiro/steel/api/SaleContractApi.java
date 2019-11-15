package com.shiro.steel.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

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
    public Object verifySaleContract(Integer id,String contractno){
    	 SaleContract saleContract = new SaleContract();
    	 saleContract.setId(id);
    	 saleContract.setContractstatus("正式临调合同");
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
    public Object confirmSaleContract(Integer id){
    	SaleContract saleContract = new SaleContract();
		 saleContract.setId(id);
		 saleContract.setInvoicestatus("已开");
    	 Boolean status = saleContractService.updateById(saleContract);
    	 if (status)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "确认已开");
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
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("saleContractAnalyseDtoList", saleContractAnalyseDtoList);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",result);
    	
    }
}
