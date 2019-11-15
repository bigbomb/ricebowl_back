package com.shiro.steel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("sale_contract")
public class SaleContract extends Model<SaleContract> implements Serializable {
	@TableId(type = IdType.AUTO)
    private Integer id;

    private String memberid;

    private String customerid;

    private String customername;

    private String contractno;

    private Date contractdate;

    private Date deliverydate;

    private String contractaddress;

	private BigDecimal contractweight;

    private BigDecimal contractamount;

    private BigDecimal actualweight;

    private BigDecimal actualamount;
    
    private BigDecimal stockouttotalfee;
    
    private BigDecimal shorttransporttotalfee;

    private String payment;

    private String settlement;
    
    private String percent;
    
    private Date depositdate;

    private String createBy;
    
    private String verifyBy;
    
    public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	private String transporttype;

    private String contracttype;

    private String contractstatus;
    
    private String invoicestatus;
    
    @TableField(exist = false)
    private String digitUppercase;
    public String getDigitUppercase() {
		return digitUppercase;
	}

	public void setDigitUppercase(String digitUppercase) {
		this.digitUppercase = digitUppercase;
	}

	private Date crt;

    private Date upt;
    
    private String remark;

    private String termContent;
    public String getTermContent() {
		return termContent;
	}

	public void setTermContent(String termContent) {
		this.termContent = termContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    public Date getContractdate() {
        return contractdate;
    }

    public void setContractdate(Date contractdate) {
        this.contractdate = contractdate;
    }
    public String getContractaddress() {
		return contractaddress;
	}

	public void setContractaddress(String contractaddress) {
		this.contractaddress = contractaddress;
	}
    public Date getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(Date deliverydate) {
        this.deliverydate = deliverydate;
    }

    public BigDecimal getContractweight() {
        return contractweight;
    }

    public void setContractweight(BigDecimal contractweight) {
        this.contractweight = contractweight;
    }

    public BigDecimal getContractamount() {
        return contractamount;
    }

    public void setContractamount(BigDecimal contractamount) {
        this.contractamount = contractamount;
    }

    public BigDecimal getActualweight() {
        return actualweight;
    }

    public void setActualweight(BigDecimal actualweight) {
        this.actualweight = actualweight;
    }

    public BigDecimal getActualamount() {
        return actualamount;
    }

    public void setActualamount(BigDecimal actualamount) {
        this.actualamount = actualamount;
    }

  

	public BigDecimal getStockouttotalfee() {
		return stockouttotalfee;
	}

	public void setStockouttotalfee(BigDecimal stockouttotalfee) {
		this.stockouttotalfee = stockouttotalfee;
	}

	public BigDecimal getShorttransporttotalfee() {
		return shorttransporttotalfee;
	}

	public void setShorttransporttotalfee(BigDecimal shorttransporttotalfee) {
		this.shorttransporttotalfee = shorttransporttotalfee;
	}

	public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getTransporttype() {
        return transporttype;
    }

    public void setTransporttype(String transporttype) {
        this.transporttype = transporttype;
    }


    public String getContracttype() {
		return contracttype;
	}

	public void setContracttype(String contracttype) {
		this.contracttype = contracttype;
	}

	public String getContractstatus() {
        return contractstatus;
    }

    public void setContractstatus(String contractstatus) {
        this.contractstatus = contractstatus;
    }

    
    public String getInvoicestatus() {
		return invoicestatus;
	}

	public void setInvoicestatus(String invoicestatus) {
		this.invoicestatus = invoicestatus;
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

	public Date getDepositdate() {
		return depositdate;
	}

	public void setDepositdate(Date depositdate) {
		this.depositdate = depositdate;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
		// TODO Auto-generated method stub
	}
}