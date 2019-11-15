package com.shiro.steel.pojo.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class SaleContractDetailDto implements Serializable {
	@TableId(type = IdType.AUTO)
    private Integer id;

	private String stockid;
	
    private String contractno;

    private String productname;

    private String productspec;

    private String productfactory;

    private String productmark;

	private Integer num;
	
    private BigDecimal weight;
    
    private BigDecimal actualweight;
    
    private BigDecimal finalweight;
    
	private BigDecimal shorttransportfee;
    
    private BigDecimal stockoutfee;

    private BigDecimal price;
    
    private String remark;

	private String status;
	
	private String stockouttype;
	
	private String warehousename;

    private String processstatus;
    
    private String deliverystatus;
    
    private String transportstatus;
    
    private String quality;
    
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String unit;
    public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	private BigDecimal total;
    public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}


    public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



//    private Date crt;
//
//    private Date upt;

    public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getStockid() {
		return stockid;
	}

	public void setStockid(String stockid) {
		this.stockid = stockid;
	}

	public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
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

	public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getStockouttype() {
        return stockouttype;
    }

    public void setStockouttype(String stockouttype) {
        this.stockouttype = stockouttype;
    }

	public String getWarehousename() {
		return warehousename;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}

	public String getProcessstatus() {
		return processstatus;
	}

	public void setProcessstatus(String processstatus) {
		this.processstatus = processstatus;
	}

	public String getDeliverystatus() {
		return deliverystatus;
	}

	public void setDeliverystatus(String deliverystatus) {
		this.deliverystatus = deliverystatus;
	}

	public String getTransportstatus() {
		return transportstatus;
	}

	public void setTransportstatus(String transportstatus) {
		this.transportstatus = transportstatus;
	}

	public BigDecimal getShorttransportfee() {
		return shorttransportfee;
	}

	public void setShorttransportfee(BigDecimal shorttransportfee) {
		this.shorttransportfee = shorttransportfee;
	}

	public BigDecimal getStockoutfee() {
		return stockoutfee;
	}

	public void setStockoutfee(BigDecimal stockoutfee) {
		this.stockoutfee = stockoutfee;
	}

  

//    public Date getCrt() {
//        return crt;
//    }
//
//    public void setCrt(Date crt) {
//        this.crt = crt;
//    }
//
//    public Date getUpt() {
//        return upt;
//    }
//
//    public void setUpt(Date upt) {
//        this.upt = upt;
//    }
}