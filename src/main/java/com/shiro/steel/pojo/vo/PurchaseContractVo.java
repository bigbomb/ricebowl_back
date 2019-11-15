package com.shiro.steel.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

public class PurchaseContractVo implements Serializable {
    private Integer id;

    private String memberid;

    private String purchasestatus;

    private String supplyername;

    private String purchaseno;

    private String contractno;

    private String customername;

    private BigDecimal purchaseweight;

    private BigDecimal purchaseamount;

    private Date purchasedate;

    private String payment;

    private String createby;

    private String verifyby;

    private String remark;

    private Date crt;

    private Date upt;
    
    private String purchaseContractDetail;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getPurchasestatus() {
        return purchasestatus;
    }

    public void setPurchasestatus(String purchasestatus) {
        this.purchasestatus = purchasestatus;
    }

    public String getSupplyername() {
        return supplyername;
    }

    public void setSupplyername(String supplyername) {
        this.supplyername = supplyername;
    }


    public String getPurchaseno() {
		return purchaseno;
	}

	public void setPurchaseno(String purchaseno) {
		this.purchaseno = purchaseno;
	}

	public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
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

    public Date getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getVerifyby() {
        return verifyby;
    }

    public void setVerifyby(String verifyby) {
        this.verifyby = verifyby;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCrt() {
        return crt;
    }

    public void setCrt(Date crt) {
        this.crt = crt;
    }

    public Date getUpt() {
        return upt;
    }

    public void setUpt(Date upt) {
        this.upt = upt;
    }

	public String getPurchaseContractDetail() {
		return purchaseContractDetail;
	}

	public void setPurchaseContractDetail(String purchaseContractDetail) {
		this.purchaseContractDetail = purchaseContractDetail;
	}
    
    
}