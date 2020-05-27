package com.shiro.steel.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.PurchaseContract;
import com.shiro.steel.entity.PurchaseContractChange;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PurchaseContractDto;
import com.shiro.steel.pojo.vo.PurchaseContractVo;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

public interface PurchaseContractChangeService extends IService<PurchaseContractChange>{

}
