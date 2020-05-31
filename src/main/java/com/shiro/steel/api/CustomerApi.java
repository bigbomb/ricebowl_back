package com.shiro.steel.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.Enum.EnumRoleType;
import com.shiro.steel.api.base.BaseApi;
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.ftp.UploadUtil;
import com.shiro.steel.pojo.dto.CustomerInfoDto;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.UserDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.CustomerInfoVo;
import com.shiro.steel.pojo.vo.UserInfoVo;
import com.shiro.steel.pojo.vo.UserVo;
import com.shiro.steel.service.CustomerInfoService;
import com.shiro.steel.service.UserService;
import com.shiro.steel.utils.ResultUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: 用户接口
 *
 * @author: jwy
 * @date: 2017/12/15
 */
@RestController
@RequestMapping(value = "CustomerApi/v1")
public class CustomerApi extends BaseApi {

    @Autowired
    private CustomerInfoService customerInfoService;


    /**
     * @desc: 查询用户列表
     *
     * @param dto 参数dto
     * @author: jwy
     * @date: 2017/12/19
     */
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET,RequestMethod.POST})//跨域
    public Object findUserByPage(@ModelAttribute ParamsDto dto,@RequestParam String memberId) {
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
        Page<CustomerInfoDto> page = new Page<>(dto.getStartPage(),dto.getPageSize());
        List<CustomerInfoDto> list = customerInfoService.findByPage(page,dto,memberId,createby);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list, page.getTotal());
    }

    /**
     * @desc: 查询用户列表
     *
     * @param dto 参数dto
     * @author: jwy
     * @date: 2017/12/19
     */
    @RequestMapping(value = "/checkPhone",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET,RequestMethod.POST})//跨域
    public Object checkPhone(@ModelAttribute ParamsDto dto,@RequestParam String memberId) {
    	CustomerInfo customerInfo = new CustomerInfo();
    	customerInfo.setMemberid(memberId);
    	customerInfo.setCompanyphone(dto.getKeyword());
    	 EntityWrapper<CustomerInfo> wrapper = new EntityWrapper<CustomerInfo>(customerInfo);
         List<CustomerInfo> customerinfo= customerInfoService.selectList(wrapper);
         return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", customerinfo);

    }
//    /**
//     * @desc: 新增用户
//     *
//     * @author: jwy
//     * @date: 2017/12/25
//     */
    @RequestMapping(value = "/addCustomer",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addCustomer(@Valid CustomerInfoVo customerInfoVo, BindingResult bindingResult) {
        return customerInfoService.addCustomer(customerInfoVo);
    }
//
//    /**
//     * @desc: 批量删除用户
//     *
//     * @author: jwy
//     * @date: 2017/12/25
//     */
    @RequestMapping(value = "/delCustomer",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delCustomer(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return customerInfoService.delCustomer(dto.getIds());
    }

    /**
     * 修改客户状态
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/editCustomerStatus", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object editCustomerStatus(ParamsDto dto) {
        if (StringUtils.isEmpty(dto.getId()) || null == dto.getType()) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return customerInfoService.editCustomerStatus(dto.getId(), dto.getType());
    }
//
//    /**
//     * 用户修改用户个人信息
//     *
//     * @author: jwy
//     * @date: 2018/1/15
//     */
//    @RequestMapping(value = "/editUserInfo", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
//    public Object editUserInfo(UserInfoVo vo) {
//        return userService.editUserInfo(vo);
//    }
    /**
     * @desc: 查询客户列表
     *
     * @param dto 参数dto
     * @author: jwy
     * @date: 2017/12/19
     */
    @RequestMapping(value = "/getCustomerList",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET,RequestMethod.POST})//跨域
    public Object getCustomerList(@ModelAttribute ParamsDto dto,@RequestParam String memberId) {
        UserInfoDto userInfoDto = new UserInfoDto();
        Subject subject = SecurityUtils.getSubject();
        userInfoDto = (UserInfoDto) subject.getPrincipal();
        String createby = null;
        Integer type = userInfoDto.getType();
        CustomerInfo customerInfo = new CustomerInfo();
        if(type==1)
        {

        }
        else if(type==2)
        {
            createby = userInfoDto.getUsername();
            customerInfo.setCreateBy(createby);
        }

    	customerInfo.setMemberid(memberId);

    	 EntityWrapper<CustomerInfo> eWrapper = new EntityWrapper<CustomerInfo>(customerInfo);
        List<CustomerInfo> list = customerInfoService.selectList(eWrapper);
        return ResultUtil.result(EnumCode.OK.getValue(), "读取成功", list);
    }
    

    /**
     * @desc: 查询客户明细
     *
     * @param dto 参数dto
     * @author: jwy
     * @date: 2017/12/19
     */
//    @RequestMapping(value = "/getCustomerDetail",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET,RequestMethod.POST})//跨域
//    public Object getCustomerDetail(@ModelAttribute ParamsDto dto,@RequestParam String memberId) {
//    	CustomerInfo customerInfo = new CustomerInfo();
//    	customerInfo.setMemberid(memberId);
//    	 EntityWrapper<CustomerInfo> eWrapper = new EntityWrapper<CustomerInfo>(customerInfo);
//        List<CustomerInfo> list = customerInfoService.selectList(eWrapper);
//        return ResultUtil.result(EnumCode.OK.getValue(), "请求成功", list);
//    }
}
