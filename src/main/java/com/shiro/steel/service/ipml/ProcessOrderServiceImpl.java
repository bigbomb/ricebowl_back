package com.shiro.steel.service.ipml;

import java.text.SimpleDateFormat;
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
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.ProcessOrder;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.entity.ProcessTemplate;
import com.shiro.steel.entity.SaleContractDetail;
import com.shiro.steel.mapper.ProcessOrderMapper;
import com.shiro.steel.mapper.ProcessTemplateMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.ProcessOrderVo;
import com.shiro.steel.service.ProcessOrderDetailService;
import com.shiro.steel.service.ProcessOrderService;
import com.shiro.steel.service.SaleContractDetailService;
import com.shiro.steel.utils.GeneratorUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
@Service
public class ProcessOrderServiceImpl extends ServiceImpl<ProcessOrderMapper, ProcessOrder> implements ProcessOrderService {
	final static String preName = "JG";
	@Autowired
	private ProcessOrderDetailService processOrderDetailService;
	@Autowired
	private SaleContractDetailService saleContractDetailService;
	@Autowired
	private ProcessTemplateMapper processTemplateMapper;
	@Override
	public Boolean addProcessOrder(ProcessOrderVo processOrderVo,Integer contractId) {
		// TODO Auto-generated method stub
		String processNo = "";
		List<SaleContractDetail> saleContractDetailList = new ArrayList<SaleContractDetail>();
		ProcessOrder processOrder = new ProcessOrder();		
		BeanCopier copier = BeanCopier.create(ProcessOrderVo.class, ProcessOrder.class, false);		
		copier.copy(processOrderVo, processOrder, null);		
		processNo = preName+GeneratorUtil.getTimeStamp();
		processOrder.setProcessno(processNo);
		processOrder.setCrt(new Date());
		UserInfoDto userInfoDto = new UserInfoDto();
 	    Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
	    processOrder.setCreateBy(userInfoDto.getUsername());
 	    super.baseMapper.insert(processOrder);
 	    //对加工模板进行操作
 	    ProcessTemplate processTemplate = new ProcessTemplate();
 	    processTemplate.setCustomerid(processOrderVo.getCustomerId());
 	    processTemplate.setMemberid(processOrderVo.getMemberId());
 	    processTemplate.setRemark(processOrderVo.getRemark());
 	    processTemplate.setWarehouseid(processOrderVo.getWarehouseId());
 	    processTemplate.setProcessfee(processOrderVo.getProcessfee());
 	    processTemplate.setProcesstype(processOrderVo.getProcesstype());
 	    processTemplate.setWarehousename(processOrderVo.getWarehouseName());
 	    processTemplate.setRemark(processOrderVo.getRemark());
 	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 	    processTemplate.setTemplatename(processOrderVo.getCustomerName()+simpleDateFormat.format(new Date()));
 	    if(StringUtils.isEmpty(processOrderVo.getTemplateId()))
 	    {
 	    	processTemplate.setCrt(new Date());
 	    	processTemplate.setCreateby(userInfoDto.getUsername());
 	 	    processTemplateMapper.insert(processTemplate);
 	    }
 	    else
 	    {
 	    	processTemplate.setId(Integer.valueOf(processOrderVo.getTemplateId()));
 	    	processTemplateMapper.updateById(processTemplate);
 	    }
 	    String processOrderDetail = processOrderVo.getProcessOrderDetail();
    	List<ProcessOrderDetail>  collection = JSONObject.parseArray(processOrderDetail, ProcessOrderDetail.class);
    	for(ProcessOrderDetail s:collection){
	  		   s.setProcessno(processNo);
	  		   s.setSaleDetailId(s.getId().toString());
	  		   s.setCrt(new Date());
	  		   SaleContractDetail scd = new SaleContractDetail();
	  		   scd.setId(s.getId());
	  		   scd.setProcessstatus("加工中");
	  		   scd.setRemark(s.getRemark());
	  		   saleContractDetailList.add(scd);
	  	   }
    	 processOrderDetailService.insertBatch(collection);
//    	 SaleContract saleContract = new SaleContract();
//    	 saleContract.setId(contractId);
//    	 saleContract.setContractstatus("已审核");
//    	 saleContractService.updateById(saleContract);
    	 
    	 Boolean status = saleContractDetailService.updateBatchById(saleContractDetailList);
    	return status;
	}
	@Override
	public List<ProcessOrderVo> findByPage(Page<ProcessOrderVo> page, ParamsDto dto,String memberId,String createby,String startTime,String endTime) {
		// TODO Auto-generated method stub
		List<ProcessOrderVo> list = super.baseMapper.findByPage(page,dto,memberId,createby,startTime,endTime);
        return list;
	}


    
}
