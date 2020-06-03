package com.shiro.steel.service.ipml;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.entity.CorpInfo;
import com.shiro.steel.entity.CustomerInfo;
import com.shiro.steel.entity.LoginLog;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.entity.SaleContractTerm;
import com.shiro.steel.entity.User;
import com.shiro.steel.entity.UserRole;
import com.shiro.steel.exception.MyException;
import com.shiro.steel.mapper.CustomerInfoMapper;
import com.shiro.steel.mapper.UserMapper;
import com.shiro.steel.pojo.dto.CustomerInfoDto;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.RolePermisDto;
import com.shiro.steel.pojo.dto.UserDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.CustomerInfoVo;
import com.shiro.steel.pojo.vo.UserInfoVo;
import com.shiro.steel.pojo.vo.UserVo;
import com.shiro.steel.service.CorpInfoService;
import com.shiro.steel.service.CustomerInfoService;
import com.shiro.steel.service.LoginLogService;
import com.shiro.steel.service.RolePermissionService;
import com.shiro.steel.service.SaleContractService;
import com.shiro.steel.service.UserRoleService;
import com.shiro.steel.service.UserService;
import com.shiro.steel.utils.ResultUtil;
import com.xiaoleilu.hutool.crypto.SecureUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoService {

	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	
	@Autowired
	private CorpInfoService corpInfoService;
	
	@Autowired
	private SaleContractService saleContractService;
	@Override
	public List<CustomerInfoDto> findByPage(Page<CustomerInfoDto> page, ParamsDto dto, String memberId,String createby) {
		// TODO Auto-generated method stub
		return super.baseMapper.findByPage(page,dto,memberId,createby);
	}

	@Override
	public Object editCustomerStatus(String id, Integer type) {
		// TODO Auto-generated method stub
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(Integer.parseInt(id));
		customerInfo.setStatus(type);
        Integer row = customerInfoMapper.updateById(customerInfo);
        return row > 0 ? ResultUtil.result(EnumCode.OK.getValue(), type == 0 ? "已禁止微信登陆" : "已允许微信登陆") : ResultUtil.result(EnumCode.INTERNAL_SERVER_ERROR.getValue(), "修改失败");
	}

	@Override
	public Object addCustomer(CustomerInfoVo customerInfoVo) {
		// TODO Auto-generated method stub
		BeanCopier copy = BeanCopier.create(CustomerInfoVo.class, CustomerInfo.class, false); 
		CustomerInfo customerInfo = new CustomerInfo();
		copy.copy(customerInfoVo, customerInfo, null);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(StringUtils.isEmpty(customerInfoVo.getId()))
			{
				UserInfoDto userInfoDto = new UserInfoDto();
		        Subject subject = SecurityUtils.getSubject();   
			    userInfoDto = (UserInfoDto) subject.getPrincipal();
			    customerInfo.setCreateBy(userInfoDto.getUsername());
				customerInfo.setCrt(sdf1.parse(sdf1.format(new Date())));
				customerInfo.setStatus(1);
				
				Integer re = super.baseMapper.insert(customerInfo);
				 if (re!=1) {
			            throw new MyException(ResultUtil.result(EnumCode.INTERNAL_SERVER_ERROR.getValue(), EnumCode.INTERNAL_SERVER_ERROR.getText(), null));
			        }
			}
			else
			{
				customerInfo.setUpt(sdf1.parse(sdf1.format(new Date())));
				Integer re = super.baseMapper.updateById(customerInfo);
				 if (re!=1) {
			            throw new MyException(ResultUtil.result(EnumCode.INTERNAL_SERVER_ERROR.getValue(), EnumCode.INTERNAL_SERVER_ERROR.getText(), null));
			        }
				
			}
		
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return ResultUtil.result(EnumCode.OK.getValue(), "保存成功");
	     
	}

	@Override
	public Object delCustomer(String[] ids) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			SaleContract saleContract = new SaleContract();
			saleContract.setCustomerid(id);
			EntityWrapper<SaleContract> wrapper = new EntityWrapper<SaleContract>(saleContract);
			saleContract =saleContractService.selectOne(wrapper);
			if(saleContract!=null)
			{
				 return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), "此客户不能删除，有关联订单存在，如要删除，请先删除关联订单，谢谢！");
			}
			else
			{
	            baseMapper.deleteById(Integer.parseInt(id));
			}

        }
        return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
	}

	@Override
	public Object bind(String companyPhone, String invitecode,String openId) {
		CorpInfo corpInfo = new CorpInfo();
		CustomerInfo customerInfo = new CustomerInfo();
//		corpInfo.setInvitecode(invitecode);
		 EntityWrapper<CorpInfo> eWrapper = new EntityWrapper<CorpInfo>(corpInfo);
		 corpInfo =  corpInfoService.selectOne(eWrapper);
		 if(corpInfo!=null)
		 {
			 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 customerInfo.setMemberid(corpInfo.getMemberId());
			 customerInfo.setCompanyphone(companyPhone);
             customerInfo.setWxopenid(openId);
	
			 CustomerInfo result = customerInfoMapper.selectOne(customerInfo);
			 if (StringUtils.isEmpty(result))
			 {
				 try {
						 customerInfo.setCrt(sdf1.parse(sdf1.format(new Date())));
						 customerInfo.setStatus(1);
						 super.baseMapper.insert(customerInfo);
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 return ResultUtil.result(EnumCode.OK.getValue(), "绑定成功",customerInfo);
			 }
			 else
			 {
				 return ResultUtil.result(EnumCode.OK.getValue(), "已经绑定过了，直接登入",result);
			 }
			

		 }
		 else
		 {
			 return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), "邀请码不存在");
		 }
		// TODO Auto-generated method stub
	}

    
}
