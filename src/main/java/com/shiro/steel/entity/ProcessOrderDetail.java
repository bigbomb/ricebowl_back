package com.shiro.steel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("process_order_detail")
public class ProcessOrderDetail implements Serializable {
	@TableId(type = IdType.AUTO)
    private Integer id;

    private String processno;

    private String saleDetailId;
    
    private String productname;

    private String productspec;

    private String productfactory;

    private String productmark;

    private String warehousename;
    
    private Integer num;

    private BigDecimal weight;
    
    private BigDecimal actualweight;
    
    private Integer stockid;

    private String unit;

    private String remark;

    private String status;

    private Date crt;

    private Date upt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessno() {
        return processno;
    }

    public void setProcessno(String processno) {
        this.processno = processno;
    }

    
    public String getSaleDetailId() {
		return saleDetailId;
	}

	public void setSaleDetailId(String saleDetailId) {
		this.saleDetailId = saleDetailId;
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

    
    public String getWarehousename() {
		return warehousename;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
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

	public BigDecimal getActualweight() {
		return actualweight;
	}

	public void setActualweight(BigDecimal actualweight) {
		this.actualweight = actualweight;
	}

	public Integer getStockid() {
		return stockid;
	}

	public void setStockid(Integer stockid) {
		this.stockid = stockid;
	}

	public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
}