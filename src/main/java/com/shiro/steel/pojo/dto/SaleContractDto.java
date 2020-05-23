package com.shiro.steel.pojo.dto;

import java.math.BigDecimal;
import java.util.Date;


public class SaleContractDto {

    private Integer id;

    private String memberid;

    private String customerid;

    private String customername;


    private String contractno;
    
    private String contractaddress;

    public String getContractaddress() {
		return contractaddress;
	}

	public void setContractaddress(String contractaddress) {
		this.contractaddress = contractaddress;
	}

	private Date contractdate;

    private Date deliverydate;

    private Date depositdate ;
    public Date getDepositdate() {
		return depositdate;
	}

	public void setDepositdate(Date depositdate) {
		this.depositdate = depositdate;
	}

	private BigDecimal contractweight;

    private BigDecimal contractamount;

    private BigDecimal actualweight;

    private BigDecimal actualamount;

    private BigDecimal finalweight;

    private BigDecimal finalamount;

    private String payment;

    private String settlement;

    private String transporttype;

    private String percent;
    public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	private String stockouttype;

    private String contractstatus;
    
    private String contracttype;
    
    private String invoiceStatus;
    
    private String createBy;
    
    private String verifyBy;

    private Date crt;

    private Date upt;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return memberId
     */
    public String getMemberid() {
        return memberid;
    }

    /**
     * @param memberid
     */
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    /**
     * @return customerId
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    /**
     * @return customerName
     */
    public String getCustomername() {
        return customername;
    }

    /**
     * @param customername
     */
    public void setCustomername(String customername) {
        this.customername = customername;
    }

    /**
     * @return contractNo
     */
    public String getContractno() {
        return contractno;
    }

    /**
     * @param contractno
     */
    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    /**
     * @return contractDate
     */
    public Date getContractdate() {
        return contractdate;
    }

    /**
     * @param contractdate
     */
    public void setContractdate(Date contractdate) {
        this.contractdate = contractdate;
    }

    /**
     * @return deliveryDate
     */
    public Date getDeliverydate() {
        return deliverydate;
    }

    /**
     * @param deliverydate
     */
    public void setDeliverydate(Date deliverydate) {
        this.deliverydate = deliverydate;
    }

    /**
     * @return contractWeight
     */
    public BigDecimal getContractweight() {
        return contractweight;
    }

    /**
     * @param contractweight
     */
    public void setContractweight(BigDecimal contractweight) {
        this.contractweight = contractweight;
    }

    /**
     * @return contractAmount
     */
    public BigDecimal getContractamount() {
        return contractamount;
    }

    /**
     * @param contractamount
     */
    public void setContractamount(BigDecimal contractamount) {
        this.contractamount = contractamount;
    }

    /**
     * @return actualWeight
     */
    public BigDecimal getActualweight() {
        return actualweight;
    }

    /**
     * @param actualweight
     */
    public void setActualweight(BigDecimal actualweight) {
        this.actualweight = actualweight;
    }

    /**
     * @return actualAmount
     */
    public BigDecimal getActualamount() {
        return actualamount;
    }

    /**
     * @param actualamount
     */
    public void setActualamount(BigDecimal actualamount) {
        this.actualamount = actualamount;
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

    /**
     * @return payment
     */
    public String getPayment() {
        return payment;
    }

    /**
     * @param payment
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * @return settlement
     */
    public String getSettlement() {
        return settlement;
    }

    /**
     * @param settlement
     */
    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    /**
     * @return transportType
     */
    public String getTransporttype() {
        return transporttype;
    }

    /**
     * @param transporttype
     */
    public void setTransporttype(String transporttype) {
        this.transporttype = transporttype;
    }

    /**
     * @return stockOutType
     */
    public String getStockouttype() {
        return stockouttype;
    }

    /**
     * @param stockouttype
     */
    public void setStockouttype(String stockouttype) {
        this.stockouttype = stockouttype;
    }

    /**
     * @return contractStatus
     */
    public String getContractstatus() {
        return contractstatus;
    }

    /**
     * @param contractstatus
     */
    public void setContractstatus(String contractstatus) {
        this.contractstatus = contractstatus;
    }

    
    public String getContracttype() {
		return contracttype;
	}

	public void setContracttype(String contracttype) {
		this.contracttype = contracttype;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getVerifyBy() {
		return verifyBy;
	}

	public void setVerifyBy(String verifyBy) {
		this.verifyBy = verifyBy;
	}

	/**
     * @return crt
     */
    public Date getCrt() {
        return crt;
    }

    /**
     * @param crt
     */
    public void setCrt(Date crt) {
        this.crt = crt;
    }

    /**
     * @return upt
     */
    public Date getUpt() {
        return upt;
    }

    /**
     * @param upt
     */
    public void setUpt(Date upt) {
        this.upt = upt;
    }
}