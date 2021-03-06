package com.shiro.steel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("stock")
public class Stock implements Serializable {
	@TableId(type = IdType.AUTO)
    private Integer id;

	private String productid;
	
    private String memberid;

    private String productname;

    private String productspec;

    private String productfactory;

    private String productmark;

    private Integer orinum;

    private Integer num;

    private BigDecimal oriweight;

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
    
    private String lockman;

    private String purchaseno;
    
    private String packingno;
    
    private String customerid;
    
    private String customername;

    private Integer supplyerid;

    private String supplyername;

    private Integer saledetailid;

    private Integer purdetailid;

    private Integer parentstockid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getProductid() {
		return productid;
	}


	public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getProductname() {
    	if ("".equals(productname))
    	{
    		return null;
    	}
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductspec() {
    	if ("".equals(productspec))
    	{
    		return null;
    	}
        return productspec;
    }

    public void setProductspec(String productspec) {
        this.productspec = productspec;
    }

    public String getProductfactory() {
    	if ("".equals(productfactory))
    	{
    		return null;
    	}
        return productfactory;
    }

    public void setProductfactory(String productfactory) {
        this.productfactory = productfactory;
    }

    public String getProductmark() {
    	if ("".equals(productmark))
    	{
    		return null;
    	}
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
    	if ("".equals(warehousename))
    	{
    		return null;
    	}
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

	public String getLockman() {
		return lockman;
	}

	public void setLockman(String lockman) {
		this.lockman = lockman;
	}

	public String getPurchaseno() {
		return purchaseno;
	}

	public void setPurchaseno(String purchaseno) {
		this.purchaseno = purchaseno;
	}

	public String getPackingno() {
		return packingno;
	}

	public void setPackingno(String packingno) {
		this.packingno = packingno;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

    public Integer getSupplyerid() {
        return supplyerid;
    }

    public void setSupplyerid(Integer supplyerid) {
        this.supplyerid = supplyerid;
    }

    public String getSupplyername() {
        return supplyername;
    }

    public void setSupplyername(String supplyername) {
        this.supplyername = supplyername;
    }

    public Integer getSaledetailid() {
        return saledetailid;
    }

    public void setSaledetailid(Integer saledetailid) {
        this.saledetailid = saledetailid;
    }

    public Integer getPurdetailid() {
        return purdetailid;
    }

    public void setPurdetailid(Integer purdetailid) {
        this.purdetailid = purdetailid;
    }

    public Integer getParentstockid() {
        return parentstockid;
    }

    public void setParentstockid(Integer parentstockid) {
        this.parentstockid = parentstockid;
    }

    public Integer getOrinum() {
        return orinum;
    }

    public void setOrinum(Integer orinum) {
        this.orinum = orinum;
    }

    public BigDecimal getOriweight() {
        return oriweight;
    }

    public void setOriweight(BigDecimal oriweight) {
        this.oriweight = oriweight;
    }
}