package com.shiro.steel.service.ipml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.mapper.SaleContractAnalyseMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.SaleContractAnalyseDto;
import com.shiro.steel.pojo.dto.SaleContractDto;
import com.shiro.steel.service.SaleContractAnalyseService;

@Service
public class SaleContractAnalyseServiceImpl  implements SaleContractAnalyseService {
	@Autowired 
	private SaleContractAnalyseMapper saleContractAnalyseMapper;

	@Override
	public List<SaleContractAnalyseDto> selectList(String deliveryno) {
		// TODO Auto-generated method stub
		return saleContractAnalyseMapper.selectList(deliveryno);
	}

  

	
}