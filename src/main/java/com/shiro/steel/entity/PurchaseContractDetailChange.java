package com.shiro.steel.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@TableName("purchase_contract_detail_change")
public class PurchaseContractDetailChange implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String purchaseno;

    private String productname;

    private String productspec;

    private String productfactory;

    private String productmark;

    private Integer num;

    private BigDecimal weight;

    private BigDecimal price;

    private String unit;

    private BigDecimal total;

    private String warehousename;

    private String stockouttype;

    private String quality;

    private String remark;

    private String status;

    private Date crt;

    private Date upt;

    private String packingno;

    private String oriProductname;

    private String oriProductspec;

    private String oriProductfactory;

    private String oriProductmark;

    private BigDecimal oriWeight;

    private BigDecimal oriPrice;

    private BigDecimal oriTotal;

    private String oriWarehousename;

    private String oriStockouttype;

    private String oriQuality;

    private String oriRemark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseno() {
        return purchaseno;
    }

    public void setPurchaseno(String purchaseno) {
        this.purchaseno = purchaseno;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductspec() {
        return productspec;
    }

    public void setProductspec(String productspec) {
        this.productspec = productspec;
    }

    public String getProductfactory() {
        return productfactory;
    }

    public void setProductfactory(String productfactory) {
        this.productfactory = productfactory;
    }

    public String getProductmark() {
        return productmark;
    }

    public void setProductmark(String productmark) {
        this.productmark = productmark;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getWarehousename() {
        return warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    public String getStockouttype() {
        return stockouttype;
    }

    public void setStockouttype(String stockouttype) {
        this.stockouttype = stockouttype;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPackingno() {
        return packingno;
    }

    public void setPackingno(String packingno) {
        this.packingno = packingno;
    }

    public String getOriProductname() {
        return oriProductname;
    }

    public void setOriProductname(String oriProductname) {
        this.oriProductname = oriProductname;
    }

    public String getOriProductspec() {
        return oriProductspec;
    }

    public void setOriProductspec(String oriProductspec) {
        this.oriProductspec = oriProductspec;
    }

    public String getOriProductfactory() {
        return oriProductfactory;
    }

    public void setOriProductfactory(String oriProductfactory) {
        this.oriProductfactory = oriProductfactory;
    }

    public String getOriProductmark() {
        return oriProductmark;
    }

    public void setOriProductmark(String oriProductmark) {
        this.oriProductmark = oriProductmark;
    }

    public BigDecimal getOriWeight() {
        return oriWeight;
    }

    public void setOriWeight(BigDecimal oriWeight) {
        this.oriWeight = oriWeight;
    }

    public BigDecimal getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(BigDecimal oriPrice) {
        this.oriPrice = oriPrice;
    }

    public BigDecimal getOriTotal() {
        return oriTotal;
    }

    public void setOriTotal(BigDecimal oriTotal) {
        this.oriTotal = oriTotal;
    }

    public String getOriWarehousename() {
        return oriWarehousename;
    }

    public void setOriWarehousename(String oriWarehousename) {
        this.oriWarehousename = oriWarehousename;
    }

    public String getOriStockouttype() {
        return oriStockouttype;
    }

    public void setOriStockouttype(String oriStockouttype) {
        this.oriStockouttype = oriStockouttype;
    }

    public String getOriQuality() {
        return oriQuality;
    }

    public void setOriQuality(String oriQuality) {
        this.oriQuality = oriQuality;
    }

    public String getOriRemark() {
        return oriRemark;
    }

    public void setOriRemark(String oriRemark) {
        this.oriRemark = oriRemark;
    }
}