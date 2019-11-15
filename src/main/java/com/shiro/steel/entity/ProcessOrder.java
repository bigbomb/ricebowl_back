package com.shiro.steel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("process_order")
public class ProcessOrder implements Serializable {
	@TableId(type = IdType.AUTO)
    private Integer id;
    
    private String memberId;
    
    private String customerName;
    
    private String contractno;

    private String processno;

    private String processtype;

    private String warehouseName;
    
    private String warehouseId;

    private BigDecimal processfee;

    private BigDecimal processweight;

    private String remark;

    private String createBy;
    
    private String verifyBy;
    
    private Date crt;

    private Date upt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    public String getProcessno() {
        return processno;
    }

    public void setProcessno(String processno) {
        this.processno = processno;
    }

    public String getProcesstype() {
        return processtype;
    }

    public void setProcesstype(String processtype) {
        this.processtype = processtype;
    }

 

    public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public BigDecimal getProcessfee() {
        return processfee;
    }

    public void setProcessfee(BigDecimal processfee) {
        this.processfee = processfee;
    }

    public BigDecimal getProcessweight() {
        return processweight;
    }

    public void setProcessweight(BigDecimal processweight) {
        this.processweight = processweight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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