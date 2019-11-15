package com.shiro.steel.api;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.entity.Attribute;
import com.shiro.steel.entity.AttributeDetail;
import com.shiro.steel.entity.CorpInfo;
import com.shiro.steel.entity.LoginLog;
import com.shiro.steel.entity.OperatingRecord;
import com.shiro.steel.entity.SysNotice;
import com.shiro.steel.pojo.dto.AttributeDetailDto;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.vo.AttributeDetailVo;
import com.shiro.steel.service.AttributeDetailService;
import com.shiro.steel.service.AttributeService;
import com.shiro.steel.service.CorpInfoService;
import com.shiro.steel.service.LoginLogService;
import com.shiro.steel.service.SysNoticeService;
import com.shiro.steel.service.OperatingRecordService;
import com.shiro.steel.utils.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "SysApi/v1")
public class SysApi {

    @Autowired
    /**
     * 属性
     */
    private AttributeService attributeService;

    @Autowired
    /**
     * 属性明细
     */
    private AttributeDetailService attributeDetailService;


    @Autowired
    /**
     * 操作记录
     */
    private OperatingRecordService operatingRecordService;

    @Autowired
    private LoginLogService loginLogService;
    
    @Autowired
    private CorpInfoService corpInfoService;

    @Autowired
    private SysNoticeService sysNoticeService;
    /**
     * @desc: 查询属性
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/findAttributesByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findAttributesByPage(ParamsDto dto) {

        Page<Attribute> page = new Page<Attribute>(dto.getStartPage(),dto.getPageSize());
        List<Attribute> list = attributeService.findAttributesByPage(page,dto);
        return ResultUtil.result(EnumCode.OK.getValue(),EnumCode.OK.getText(),list,page.getTotal());
    }

    /**
     * @desc: 查询属性明细
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/findAttributesDetailByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findAttributesDetailByPage(ParamsDto dto) {

        Page<AttributeDetail> page = new Page<AttributeDetail>(dto.getStartPage(),dto.getPageSize());
        List<AttributeDetail> list = attributeDetailService.findAttributeDetailByPage(page,dto);
        return ResultUtil.result(EnumCode.OK.getValue(),EnumCode.OK.getText(),list,page.getTotal());
    }

    /**
     * @desc: 新增属性
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/addAttributes" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addAttributes(@Valid Attribute attribute, BindingResult bindingResult) {
        return attributeService.addAttributes(attribute);
    }

    /**
     * @desc: 新增属性明细
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/addAttributeDetail" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addAttributeDetail(@Valid AttributeDetailVo vo, BindingResult bindingResult) {
        return attributeDetailService.addAttributeDetail(vo);
    }

    /**
     * @desc: 删除属性
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/delAttributes",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delAttributes(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return attributeService.delAttributes(dto.getIds());
    }

    /**
     * @desc: 删除属性明细
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/delAttributeDetails",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delAttributeDetails(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return attributeDetailService.delAttributeDetails(dto.getIds());
    }

   /**
    * @desc: 根据属性id查询属性
    * 
    * @author: jwy
    * @date: 2018/1/2
    */
    @RequestMapping(value = "/findAttributeDetailByAttrId",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object selAttributeDetailsByAttrId(ParamsDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        List<AttributeDetailDto> list = attributeDetailService.findAttributeDetailByAttrId(dto.getId());
        if (null == list || list.isEmpty()) {
            return ResultUtil.result(EnumCode.GONE.getValue(), "没有记录");
        }
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list);
    }

    /**
     * 查询操作记录
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findOperatingRecordByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findOperatingRecordByPage(ParamsDto dto) {

        Page<OperatingRecord> page = new Page<OperatingRecord>(dto.getStartPage(),dto.getPageSize());
        List<OperatingRecord> list = operatingRecordService.findOperatingRecordByPage(page,dto);
        return ResultUtil.result(EnumCode.OK.getValue(),EnumCode.OK.getText(),list,page.getTotal());

    }

    /**
     * 用户登录日志
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/findUserLoginLogByPage", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findUserLoginLogByPage(ParamsDto dto) {

        Page<LoginLog> page = new Page<LoginLog>(dto.getStartPage(), dto.getPageSize());
        List<LoginLog> list = loginLogService.findUserLoginLogByPage(page, dto);
        return ResultUtil.result(EnumCode.OK.getValue(), EnumCode.OK.getText(), list, page.getTotal());

    }

    /**
     * 统计用户登录
     *
     * @author: jwy
     * @date: 2018/1/11
     */
    @RequestMapping(value = "/findUserLoginTotal", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findUserLoginTotal() {
        return loginLogService.findUserLoginTotal();
    }

    /**
     * 访问统计
     *
     * @author: jwy
     * @date: 2018/1/11
     */
    @RequestMapping(value = "/findUserReqTotal", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findUserReqTotal() {
        return operatingRecordService.findUserReqTotal();
    }

    /**
     * 获取企业信息
     *
     * @author: jwy
     * @date: 2018/1/11
     */
    @RequestMapping(value = "/corpInfo", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object corpInfo(String memberId) {
    	Map<String, Object> corpMap = new HashMap<String, Object>();
    	corpMap.put("memberId",memberId);
        return corpInfoService.selectByMap(corpMap);
    }

    /**
     * 获取企业信息
     *
     * @author: jwy
     * @date: 2018/1/11
     */
    @RequestMapping(value = "/saveCorpInfo", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object saveCorpInfo(@Valid CorpInfo corpInfo) {
        return corpInfoService.saveCorpInfo(corpInfo);
    }
    
    /**
     * @desc: 查询notice
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/findNoticeByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findNoticeByPage(ParamsDto dto) {

        Page<SysNotice> page = new Page<SysNotice>(dto.getStartPage(),dto.getPageSize());
        List<SysNotice> list = sysNoticeService.findNoticeByPage(page,dto);
        return ResultUtil.result(EnumCode.OK.getValue(),EnumCode.OK.getText(),list,page.getTotal());
    }
    
    /**
     * @desc: 新增公告
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/addNotice" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addNotice(@Valid SysNotice sysNotice, BindingResult bindingResult) {
    	if(StringUtils.isEmpty(sysNotice.getId()))
    	{
    		sysNotice.setCrt(new Date());
    	}
    	else
    	{
    		sysNotice.setUpt(new Date());
    	}
    	Boolean status = sysNoticeService.insertOrUpdate(sysNotice);
    	if(status)
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
    	}else
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "保存失败");
    	}
        
    }
    
    /**
     * @desc: 删除公告
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/delNotice",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delNotice(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        List idsList=new ArrayList();
        idsList=Arrays.asList(dto.getIds());
        Boolean status = sysNoticeService.deleteBatchIds(idsList);
       	if(status)
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    	}else
    	{
    		return ResultUtil.result(EnumCode.OK.getValue(), "删除失败");
    	}
    }
    
    
}
