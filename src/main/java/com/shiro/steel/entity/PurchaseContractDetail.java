package com.shiro.steel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("purchase_contract_detail")
public class PurchaseContractDetail implements Serializable {
	@TableId(type = IdType.AUTO)
    private Integer id;

    private String purchaseno;

    private String productname;

    private String productspec;
    
    private String packingno;

    private String productfactory;

    private String productmark;

    private Integer instocknum;

    private Integer num;

    private BigDecimal weight;

    private BigDecimal instockweight;

    private BigDecimal price;

    private String unit;

    private BigDecimal totalamount;

    private BigDecimal instocktotalamount;
    
    private String warehousename;

    private String stockouttype;

    private String quality;
    
    private String status;

    private String remark;

    private Date crt;

    private Date upt;

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

    
    public String getPackingno() {
		return packingno;
	}

	public void setPackingno(String packingno) {
		this.packingno = packingno;
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

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public BigDecimal getInstocktotalamount() {
        return instocktotalamount;
    }

    public void setInstocktotalamount(BigDecimal instocktotalamount) {
        this.instocktotalamount = instocktotalamount;
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

    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

    public Integer getInstocknum() {
        return instocknum;
    }

    public void setInstocknum(Integer instocknum) {
        this.instocknum = instocknum;
    }

    public BigDecimal getInstockweight() {
        return instockweight;
    }

    public void setInstockweight(BigDecimal instockweight) {
        this.instockweight = instockweight;
    }
}