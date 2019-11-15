package com.shiro.steel.service.ipml;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.entity.SaleContract;
import com.shiro.steel.entity.SaleContractTerm;
import com.shiro.steel.mapper.SaleContractMapper;
import com.shiro.steel.mapper.SaleContractTermMapper;
import com.shiro.steel.service.SaleContractService;
import com.shiro.steel.service.SaleContractTermService;
@Service
public class SaleContractTermServiceImpl extends ServiceImpl<SaleContractTermMapper, SaleContractTerm> implements SaleContractTermService{

}
