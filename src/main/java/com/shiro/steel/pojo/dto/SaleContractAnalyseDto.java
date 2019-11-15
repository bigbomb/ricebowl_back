package com.shiro.steel.pojo.dto;

import java.math.BigDecimal;

public class SaleContractAnalyseDto {
	private String contractno;
	private String contractdate;
	private String customername;
	private BigDecimal actualweight;
	private BigDecimal actualamount;
	private BigDecimal purchaseweight;
	private BigDecimal purchaseamount;
	private BigDecimal processfee;
	private BigDecimal transportweight;
	private BigDecimal transportfee;
	private BigDecimal stockouttotalfee;
	private BigDecimal shorttransporttotalfee;
	private BigDecimal overtimefee;
	private String createby;
	public String getContractno() {
		return contractno;
	}
	public void setContractno(String contractno) {
		this.contractno = contractno;
	}
	public String getContractdate() {
		return contractdate;
	}
	public void setContractdate(String contractdate) {
		this.contractdate = contractdate;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public BigDecimal getActualweight() {
		return actualweight;
	}
	public void setActualweight(BigDecimal actualweight) {
		this.actualweight = actualweight;
	}
	public BigDecimal getActualamount() {
		return actualamount;
	}
	public void setActualamount(BigDecimal actualamount) {
		this.actualamount = actualamount;
	}
	public BigDecimal getPurchaseweight() {
		return purchaseweight;
	}
	public void setPurchaseweight(BigDecimal purchaseweight) {
		this.purchaseweight = purchaseweight;
	}
	public BigDecimal getPurchaseamount() {
		return purchaseamount;
	}
	public void setPurchaseamount(BigDecimal purchaseamount) {
		this.purchaseamount = purchaseamount;
	}
	public BigDecimal getProcessfee() {
		return processfee;
	}
	public void setProcessfee(BigDecimal processfee) {
		this.processfee = processfee;
	}
	public BigDecimal getTransportweight() {
		return transportweight;
	}
	public void setTransportweight(BigDecimal transportweight) {
		this.transportweight = transportweight;
	}
	public BigDecimal getTransportfee() {
		return transportfee;
	}
	public void setTransportfee(BigDecimal transportfee) {
		this.transportfee = transportfee;
	}
	public BigDecimal getStockouttotalfee() {
		return stockouttotalfee;
	}
	public void setStockouttotalfee(BigDecimal stockouttotalfee) {
		this.stockouttotalfee = stockouttotalfee;
	}
	public BigDecimal getShorttransporttotalfee() {
		return shorttransporttotalfee;
	}
	public void setShorttransporttotalfee(BigDecimal shorttransporttotalfee) {
		this.shorttransporttotalfee = shorttransporttotalfee;
	}
	public BigDecimal getOvertimefee() {
		return overtimefee;
	}
	public void setOvertimefee(BigDecimal overtimefee) {
		this.overtimefee = overtimefee;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
}
