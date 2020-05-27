package com.shiro.steel.pojo.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.util.StringUtils;

public class TransportOrderDto implements Serializable {

    private Integer id;

    private String verifyby;

    private String createby;

    private Date upt;

    private String transportno;
    
    private String deliveryno;

    private String contractno;

    private String customername;

    private String memberid;

    private Date crt;

    private BigDecimal transportfee;

    private BigDecimal transportweight;

    private String transportaddress;

    private String warehouse;

    private String vehiclenumber;

    private String carrier;
    
    private String remark;
    
    private String status;
    
    private BigDecimal transporttotalfee;
    
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVerifyby() {
        return verifyby;
    }

    public void setVerifyby(String verifyby) {
        this.verifyby = verifyby;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getUpt() {
        return upt;
    }

    public void setUpt(Date upt) {
        this.upt = upt;
    }

    public String getTransportno() {
        return transportno;
    }

    public void setTransportno(String transportno) {
        this.transportno = transportno;
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

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public Date getCrt() {
        return crt;
    }

    public void setCrt(Date crt) {
        this.crt = crt;
    }

    public BigDecimal getTransportfee() {
        return transportfee;
    }

    public void setTransportfee(BigDecimal transportfee) {
        this.transportfee = transportfee;
    }

    public BigDecimal getTransportweight() {
        return transportweight;
    }

    public void setTransportweight(BigDecimal transportweight) {
        this.transportweight = transportweight;
    }

    public String getTransportaddress() {
        return transportaddress;
    }

    public void setTransportaddress(String transportaddress) {
        this.transportaddress = transportaddress;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTransporttotalfee() {
		return transporttotalfee;
	}

	public void setTransporttotalfee(BigDecimal transporttotalfee) {
		this.transporttotalfee = transporttotalfee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}