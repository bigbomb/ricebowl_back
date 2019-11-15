package com.shiro.steel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("delivery_order")
public class DeliveryOrder implements Serializable {
	@TableId(type = IdType.AUTO)
    private Integer id;

    private String memberid;

    private String deliveryno;
    
    private BigDecimal weight;
    
    private BigDecimal finalweight;

	private String contractno;

    private Date startTime;
    
    private Date endTime;

    private String customername;
    
    private String deliverymethod;

    private String warehouseid;

    private String warehousename;

    private String warehouseaddress;

    private String warehousephone;

    private String warehousefax;

    private String vehiclenumber;

    private String remark;

    private String createBy;
    
    private String verifyBy;
    
    private Date crt;

    private Date upt;
    
    private BigDecimal overtimefee;

    private Integer isItemRight;
    
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
	
	
    public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
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

    
	public BigDecimal getOvertimefee() {
		return overtimefee;
	}

	public void setOvertimefee(BigDecimal overtimefee) {
		this.overtimefee = overtimefee;
	}

	
	public Integer getIsItemRight() {
		return isItemRight;
	}

	public void setIsItemRight(Integer isItemRight) {
		this.isItemRight = isItemRight;
	}

	public BigDecimal getFinalweight() {
		return finalweight;
	}

	public void setFinalweight(BigDecimal finalweight) {
		this.finalweight = finalweight;
	}
    
    
}