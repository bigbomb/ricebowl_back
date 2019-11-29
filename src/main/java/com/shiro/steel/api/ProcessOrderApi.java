package com.shiro.steel.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.api.base.BaseApi;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.entity.ProcessTemplate;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.ProcessOrderVo;
import com.shiro.steel.service.ProcessOrderDetailService;
import com.shiro.steel.service.ProcessOrderService;
import com.shiro.steel.service.ProcessTemplateService;
import com.shiro.steel.utils.ResultUtil;

@RestController
@RequestMapping(value = "ProcessOrderApi/v1")
public class ProcessOrderApi extends BaseApi{
	
	@Autowired
	private ProcessOrderService processOrderService;
	
	@Autowired
	private ProcessOrderDetailService  processOrderDetailService;
    
    @Autowired
    private ProcessTemplateService  processTemplateService;
	/**
     * @desc: 新增报价单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/addProcessOrder" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addProcessOrder(@Validated ProcessOrderVo processOrderVo,Integer contractId, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResultUtil.result(EnumCode.OK.getValue(), errorList.toString());
        	}
    	    Boolean status = processOrderService.addProcessOrder(processOrderVo,contractId);
    	    if (status)
    	     {
    	    	 return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    	     }else
    	     {
    	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "保存失败");
    	     }
    
    }
    
    @RequestMapping(value = "/findByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByPage(ParamsDto dto,String memberId,String startTime,String endTime) throws ParseException{
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
        Page<ProcessOrderVo> page = new Page<>(dto.getStartPage(),dto.getPageSize());
         List<ProcessOrderVo> list = processOrderService.findByPage(page, dto,memberId,createby,startTimeString,endTimeString);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list, page.getTotal(),page.getPages());
    }
    
    @RequestMapping(value = "/findByPageList" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByPageList(String processNo){
    	ProcessOrderDetail processOrderDetail = new ProcessOrderDetail();
    	processOrderDetail.setProcessno(processNo);
     	 EntityWrapper<ProcessOrderDetail> wrapper = new EntityWrapper<ProcessOrderDetail>(processOrderDetail);
         List<ProcessOrderDetail> list = processOrderDetailService.selectList(wrapper);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list);
    }
    
    @RequestMapping(value = "/delProcessOrder" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delProcessOrder(ParamsDto dto,String[] processNos,String[] saleContractNos){
    	 if (null == dto.getIds() || dto.getIds().length == 0) {
             return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
         }
    	 Boolean result =  processOrderService.delProcessOrder(dto,processNos,saleContractNos);
    	 if(result) {
    		 return ResultUtil.result(EnumCode.OK.getValue(), "删除成功", null);
    	 }
    	 else {
    		 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "删除失败", null);
    	 }
       
    }
    
    @RequestMapping(value = "/findByCustomerId" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByCustomerId(String customerId,String memberId){
    	ProcessTemplate processTemplate = new ProcessTemplate();
    	processTemplate.setCustomerid(customerId);
    	processTemplate.setMemberid(memberId);
     	EntityWrapper<ProcessTemplate> wrapper = new EntityWrapper<ProcessTemplate>(processTemplate);
        List<ProcessTemplate> list = processTemplateService.selectList(wrapper);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list);
    }
   
    @RequestMapping(value = "/findByOneCustomerId" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findByOneCustomerId(String customerId,String memberId, String id ){
    	ProcessTemplate processTemplate = new ProcessTemplate();
    	processTemplate.setCustomerid(customerId);
    	processTemplate.setMemberid(memberId);
    	processTemplate.setId(Integer.valueOf(id));
     	EntityWrapper<ProcessTemplate> wrapper = new EntityWrapper<ProcessTemplate>(processTemplate);
        ProcessTemplate  result = processTemplateService.selectOne(wrapper);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", result);
    }
    
    /**
     * @desc: 删除合同
     *
     * @author: jwy
     * @date: 2017/12/25
     */
    @RequestMapping(value = "/delTemplateId",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delTemplateId(String id) {
     boolean r =  processTemplateService.deleteById(Integer.valueOf(id));
 	 if(r)
	     {
	    	 return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
	     }
 	 else
	     {
	    	 return ResultUtil.result(EnumCode.EXCPTION_ERROR.getValue(), "删除失败");
	     }
     }
}
