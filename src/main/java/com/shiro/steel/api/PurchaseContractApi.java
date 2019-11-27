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
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.api.base.BaseApi;
import com.shiro.steel.entity.PurchaseContract;
import com.shiro.steel.entity.PurchaseContractDetail;
import com.shiro.steel.entity.Supplyer;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PurchaseContractDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.PurchaseContractVo;
import com.shiro.steel.service.PurchaseContractDetailService;
import com.shiro.steel.service.PurchaseContractService;
import com.shiro.steel.service.SupplyerService;
import com.shiro.steel.utils.ResultUtil;

/**
 * @desc: 用户接口
 *
 * @author: jwy
 * @date: 2017/12/15
 */
@RestController
@RequestMapping(value = "PurchaseContractApi/v1")
public class PurchaseContractApi extends BaseApi {

    @Autowired
    private PurchaseContractService purchaseContractService;
    @Autowired
    private PurchaseContractDetailService purchaseContractDetailService;
    
    @Autowired
    private SupplyerService supplyerService;
       /**
     * @desc: 查询订单
     *
     * @param dto 参数dto
     * @author: jwy
     * @throws ParseException 
     * @date: 2017/12/19
     */
    @RequestMapping(value = "/findPurchaseContractByPage",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.POST})//跨域
    public Object findUserByPage(ParamsDto dto,String statusTab,String invoiceStatus,String memberId, String startTime,String endTime) throws ParseException{
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
        Page<PurchaseContractDto> page = new Page<>(dto.getStartPage(),dto.getPageSize());
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


         List<PurchaseContractDto> list = purchaseContractService.findPurchaseContractByStatusPage(page,dto,statusTab,invoiceStatus,createby,startTimeString,endTimeString);
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
    public Object addUser(@Valid PurchaseContractVo purchaseContractVo, BindingResult bindingResult) {


       return purchaseContractService.addContract(purchaseContractVo);
    }
    
    /**
     * @desc: 查询订单明细
     *
     * @param dto 参数dto
     * @author: jwy
     * @throws Exception 
     * @date: 2017/12/19
     */
    @RequestMapping(value = "/findPurchaseContractDetailByPage",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findSaleContractDetailByPage(ParamsDto dto,String memberId,String purchaseno) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
    	Map<String, Object> selectMap = new HashMap<String, Object>();
    	selectMap.put("purchaseno", purchaseno);
    	//获取订单主要信息
    	List<PurchaseContract> PurchaseContractDto = purchaseContractService.selectByMap(selectMap);
        //获取订单商品明细
        List<PurchaseContractDetail> list = purchaseContractDetailService.selectByMap(selectMap);
        result.put("purchaseContract" ,PurchaseContractDto.get(0));
        result.put("purchaseContractDetail", list);

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
     Integer r =  purchaseContractService.delBatchIds(Arrays.asList(ids),Arrays.asList(contractnos));
 	 if(r>0)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
	     }
 	 else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "禁止删除已审核单据");
	     }
     }
    
    @RequestMapping(value = "/copyContract" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object copyContract(String contractno){
    	 Boolean status = purchaseContractService.copyContract(contractno);
    	 if (status)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "复制成功");
	     }else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "复制失败");
	     }
    }
    
    
    
    @RequestMapping(value = "/verifyPurchaseContract" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object verifySaleContract(Integer id,String purchaseno,String memberId){
    	 Integer status = purchaseContractService.updateByVerify(id,purchaseno,memberId);
    	 if (status>0)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "审核成功");
	     }else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "审核失败");
	     }
    }
    
    @RequestMapping(value = "/confirmPurchaseContract" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object confirmPurchaseContract(Integer id){
		 PurchaseContract purchaseContract = new PurchaseContract();
		 purchaseContract.setId(id);
		 purchaseContract.setInvoicestatus("已收到");
    	 Boolean status = purchaseContractService.updateById(purchaseContract);
    	 if (status)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "确认已收到");
	     }else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "确认失败");
	     }
    }
    
    
    @RequestMapping(value = "/findSupplyerByNoPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findSupplyerByNoPage(String memberId){
    	Map<String, Object> selectMap = new HashMap<String, Object>();
    	selectMap.put("memberId", memberId);
    	//获取订单主要信息
    	List<Supplyer> supplyerList = supplyerService.selectByMap(selectMap);
    	 if (supplyerList.size()>0)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",supplyerList);
	     }else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "读取失败");
	     }
    }
    
    @RequestMapping(value = "/findSupplyerByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findSupplyerByPage(ParamsDto dto,String memberId){
    	Map<String, Object> selectMap = new HashMap<String, Object>();
    	selectMap.put("memberId", memberId);
    	//获取订单主要信息
    	List<Supplyer> supplyerList = supplyerService.selectByMap(selectMap);
    	 if (supplyerList.size()>0)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "读取成功",supplyerList);
	     }else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "读取失败");
	     }
    }
    
    /**
     * @desc: 保存供应商
     *
     * @author: jwy
     * @date: 2017/12/25
     */
    @RequestMapping(value = "/saveSupplyer" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object saveSupplyer(Supplyer supplyer){
    	Boolean status  =  supplyerService.insertOrUpdate(supplyer);
    	 if (status)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
	     }else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "保存失败");
	     }
    }
    
    /**
     * @desc: 删除供应商
     *
     * @author: jwy
     * @date: 2017/12/25
     */
    @RequestMapping(value = "/delSupplyerRow",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delSupplyerRow(Integer id) {
    	Boolean status = supplyerService.deleteById(id);
        if(status)
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    	}else
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "删除失败");
    	}
     }
}
