package com.shiro.steel.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class ProcessOrderVo implements Serializable {
    private Integer id;
    private String memberId;
    private String customerName;
    private String customerId;
    private String templateId;
    private String contractno;
    
    private String processno;
    @NotEmpty(message = "加工类型不能为空")
    private String processtype;
    @NotEmpty(message = "仓库名不能为空")
    private String warehouseName;
    
    private String warehouseId;
    private BigDecimal processfee;
    private BigDecimal processweight;

    private String remark;
    
    private String processOrderDetail;
    
    private String createby;

    private Date crt;

    private Date upt;

    private String status;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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
    
    public String getProcessOrderDetail() {
		return processOrderDetail;
	}

	public void setProcessOrderDetail(String processOrderDetail) {
		this.processOrderDetail = processOrderDetail;
	}

	
	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}