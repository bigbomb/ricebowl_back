package com.shiro.steel.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DeliveryOrderVo implements Serializable {
    private Integer id;

    private String memberid;

    private String deliveryno;
    
    private String contractno;

    private Date startTime;
    
    private Date endTime;
    
    private String customername;
    
    private String customerid;

    private String deliverymethod;
    
    private String warehouseid;

    private String warehousename;

    private String warehouseaddress;

    private String warehousephone;

    private String warehousefax;

    private String vehiclenumber;

    private String remark;

    private Date crt;

    private Date upt;
    
    private String createby;
    private BigDecimal weight;
    
    private BigDecimal finalweight;
    
    private BigDecimal overtimefee;

    private Integer feeoption;

    private BigDecimal liftingfee;

    private String deliveryOrderDetail;

    private Integer isItemRight;
    
    private String status;
    
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
    

    public String getDeliveryno() {
		return deliveryno;
	}

	public void setDeliveryno(String deliveryno) {
		this.deliveryno = deliveryno;
	}

	public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getDeliverymethod() {
		return deliverymethod;
	}

	public void setDeliverymethod(String deliverymethod) {
		this.deliverymethod = deliverymethod;
	}

	public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getWarehousename() {
        return warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    public String getWarehouseaddress() {
        return warehouseaddress;
    }

    public void setWarehouseaddress(String warehouseaddress) {
        this.warehouseaddress = warehouseaddress;
    }

    public String getWarehousephone() {
        return warehousephone;
    }

    public void setWarehousephone(String warehousephone) {
        this.warehousephone = warehousephone;
    }

    public String getWarehousefax() {
        return warehousefax;
    }

    public void setWarehousefax(String warehousefax) {
        this.warehousefax = warehousefax;
    }

    public String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
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



	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public BigDecimal getFinalweight() {
		return finalweight;
	}

	public void setFinalweight(BigDecimal finalweight) {
		this.finalweight = finalweight;
	}

	
	public BigDecimal getOvertimefee() {
		return overtimefee;
	}

	public void setOvertimefee(BigDecimal overtimefee) {
		this.overtimefee = overtimefee;
	}

    public Integer getFeeoption() {
        return feeoption;
    }

    public void setFeeoption(Integer feeoption) {
        this.feeoption = feeoption;
    }

    public BigDecimal getLiftingfee() {
        return liftingfee;
    }

    public void setLiftingfee(BigDecimal liftingfee) {
        this.liftingfee = liftingfee;
    }

    public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getDeliveryOrderDetail() {
		return deliveryOrderDetail;
	}

	public void setDeliveryOrderDetail(String deliveryOrderDetail) {
		this.deliveryOrderDetail = deliveryOrderDetail;
	}


	public Integer getIsItemRight() {
		return isItemRight;
	}

	public void setIsItemRight(Integer isItemRight) {
		this.isItemRight = isItemRight;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}