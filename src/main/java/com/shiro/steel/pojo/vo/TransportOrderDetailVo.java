package com.shiro.steel.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

public class TransportOrderDetailVo implements Serializable {

    private Integer id;

    private String transportno;

    private String saledetailid;

    private String productname;

    private String productspec;

    private String productfactory;

    private String productmark;

    private Integer num;

    private BigDecimal actualweight;

    private BigDecimal finalweight;

    private String unit;

    private Integer stockid;

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

    public String getTransportno() {
        return transportno;
    }

    public void setTransportno(String transportno) {
        this.transportno = transportno;
    }

    public String getSaledetailid() {
        return saledetailid;
    }

    public void setSaledetailid(String saledetailid) {
        this.saledetailid = saledetailid;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getStockid() {
        return stockid;
    }

    public void setStockid(Integer stockid) {
        this.stockid = stockid;
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