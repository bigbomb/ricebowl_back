package com.shiro.steel.pojo.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.shiro.steel.entity.SaleContractDetail;

/**
 * @desc: 用户vo
 * 
 * @author: jwy
 * @date: 2017/12/16
 */
public class ContractVo {
	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	private String memberid;

    private String customerid;
    
    private String contractno;
    public String getCustomername() {
		return customername;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public Date getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(Date deliverydate) {
		this.deliverydate = deliverydate;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getTransporttype() {
		return transporttype;
	}

	public void setTransporttype(String transporttype) {
		this.transporttype = transporttype;
	}


	public BigDecimal getContractweight() {
		return contractweight;
	}

	public void setContractweight(BigDecimal contractweight) {
		this.contractweight = contractweight;
	}

	public BigDecimal getContractamount() {
		return contractamount;
	}

	public void setContractamount(BigDecimal contractamount) {
		this.contractamount = contractamount;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}



	/**
     * 客户名称
     */
    @NotEmpty(message = "客户名称不能为空")
    private String customername;
    
    /**
     * 客户名称
     */
    @NotEmpty(message = "合同地址不能为空")
    private String contractaddress;
    
    
    public String getContractaddress() {
		return contractaddress;
	}

	public void setContractaddress(String contractaddress) {
		this.contractaddress = contractaddress;
	}



	/**
     * 交货日期
     */

    private Date deliverydate;
    /**
     * 付款方式
     */
    @NotEmpty(message = "付款方式不能为空")
    // @Pattern(regexp = "^[a-zA-Z]\\w{5,17}$", message = "密码以字母开头，长度在6~18之间，只能包含字符、数字和下划线")
    private String payment;

    /**
     * 状态
     */
    private String contractstatus;

    /**
     * 运输方式
     */
    @NotEmpty(message = "运输方式不能为空")
    private String transporttype;
    
    
    
    
    
    /**
     * 合同类型
     */
   
    private String contracttype;
    
    
    /**
     * 合同重量
     */
    private BigDecimal contractweight;
    
    /**
     * 合同金额
     */

    private BigDecimal contractamount;
    

    private BigDecimal shorttransporttotalfee;
    
    private BigDecimal stockouttotalfee;
    
    private String remark;



	public String getContracttype() {
		return contracttype;
	}

	public void setContracttype(String contracttype) {
		this.contracttype = contracttype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



	/**
     * 结算方式
     */
    @NotBlank(message = "结算方式不能为空")
    private String settlement;


    public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}



	private String percent;
    
	private Date depositdate;
	
  

	public Date getDepositdate() {
		return depositdate;
	}

	public void setDepositdate(Date depositdate) {
		this.depositdate = depositdate;
	}



	private String saleContractDetail;


	public String getSaleContractDetail() {
		return saleContractDetail;
	}

	public void setSaleContractDetail(String saleContractDetail) {
		this.saleContractDetail = saleContractDetail;
	}

	public String getContractstatus() {
		return contractstatus;
	}

	public void setContractstatus(String contractstatus) {
		this.contractstatus = contractstatus;
	}

	public BigDecimal getShorttransporttotalfee() {
		return shorttransporttotalfee;
	}

	public void setShorttransporttotalfee(BigDecimal shorttransporttotalfee) {
		this.shorttransporttotalfee = shorttransporttotalfee;
	}

	public BigDecimal getStockouttotalfee() {
		return stockouttotalfee;
	}

	public void setStockouttotalfee(BigDecimal stockouttotalfee) {
		this.stockouttotalfee = stockouttotalfee;
	}


    
}
