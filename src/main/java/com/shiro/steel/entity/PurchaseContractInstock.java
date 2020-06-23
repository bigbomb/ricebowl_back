package com.shiro.steel.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@TableName("purchase_contract_instock")
public class PurchaseContractInstock implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String memberid;

    private String purchasestatus;

    private String invoicestatus;

    private String supplyername;

    private String purchaseno;

    private String contractno;

    private String purchaseinstockno;

    private String customername;

    private BigDecimal totalweight;

    private BigDecimal totalamount;

    private String payment;

    private String createby;

    private String verifyby;

    private String remark;

    private Date crt;

    private Date upt;

    private Integer supplyerid;

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

    public String getInvoicestatus() {
        return invoicestatus;
    }

    public void setInvoicestatus(String invoicestatus) {
        this.invoicestatus = invoicestatus;
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

    public String getPurchaseinstockno() {
        return purchaseinstockno;
    }

    public void setPurchaseinstockno(String purchaseinstockno) {
        this.purchaseinstockno = purchaseinstockno;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public BigDecimal getTotalweight() {
        return totalweight;
    }

    public void setTotalweight(BigDecimal totalweight) {
        this.totalweight = totalweight;
    }

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
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

    public Integer getSupplyerid() {
        return supplyerid;
    }

    public void setSupplyerid(Integer supplyerid) {
        this.supplyerid = supplyerid;
    }
}