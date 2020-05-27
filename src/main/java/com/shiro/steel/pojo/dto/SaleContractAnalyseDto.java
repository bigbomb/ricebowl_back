package com.shiro.steel.pojo.dto;

import java.math.BigDecimal;

public class SaleContractAnalyseDto {
	private String contractno;
	private String transportno;
	private String deliveryno;
	private String contractdate;
	private String customername;
	private Integer stockId;
	private BigDecimal actualweight;
	private BigDecimal finalweight;
	private BigDecimal finalamount;
	private BigDecimal actualamount;
	private BigDecimal saleprice;
	private BigDecimal purchaseprice;
	private BigDecimal processfee;
	private BigDecimal transportweight;
	private BigDecimal transportfee;
	private BigDecimal stockoutfee;
	private BigDecimal stockouttotalfee;
	private BigDecimal shorttransporttotalfee;
	private BigDecimal overtimefee;
	private String createby;
    private BigDecimal grossprofit;
	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public String getTransportno() {
		return transportno;
	}

	public void setTransportno(String transportno) {
		this.transportno = transportno;
	}

	public String getDeliveryno() {
		return deliveryno;
	}

	public void setDeliveryno(String deliveryno) {
		this.deliveryno = deliveryno;
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

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public BigDecimal getActualweight() {
		return actualweight;
	}

	public void setActualweight(BigDecimal actualweight) {
		this.actualweight = actualweight;
	}

	public BigDecimal getFinalweight() {
		return finalweight;
	}

	public void setFinalweight(BigDecimal finalweight) {
		this.finalweight = finalweight;
	}

	public BigDecimal getFinalamount() {
		return finalamount;
	}

	public void setFinalamount(BigDecimal finalamount) {
		this.finalamount = finalamount;
	}

	public BigDecimal getActualamount() {
		return actualamount;
	}

	public void setActualamount(BigDecimal actualamount) {
		this.actualamount = actualamount;
	}

	public BigDecimal getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}

	public BigDecimal getPurchaseprice() {
		return purchaseprice;
	}

	public void setPurchaseprice(BigDecimal purchaseprice) {
		this.purchaseprice = purchaseprice;
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

	public BigDecimal getStockoutfee() {
		return stockoutfee;
	}

	public void setStockoutfee(BigDecimal stockoutfee) {
		this.stockoutfee = stockoutfee;
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

	public BigDecimal getGrossprofit() {
		return grossprofit;
	}

	public void setGrossprofit(BigDecimal grossprofit) {
		this.grossprofit = grossprofit;
	}
}
