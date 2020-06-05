package com.shiro.steel.pojo.dto;

import java.math.BigDecimal;

public class DeliveryOrderDetailPurDto {
    private BigDecimal actualweight;
    private Integer actualnum;
    private Integer id;
    private Integer saledetailid;
    private String deliveryno;
    private BigDecimal weightbalance;
    private Integer numbalance;
    private Integer stockid;

    public BigDecimal getActualweight() {
        return actualweight;
    }

    public void setActualweight(BigDecimal actualweight) {
        this.actualweight = actualweight;
    }

    public Integer getActualnum() {
        return actualnum;
    }

    public void setActualnum(Integer actualnum) {
        this.actualnum = actualnum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaledetailid() {
        return saledetailid;
    }

    public void setSaledetailid(Integer saledetailid) {
        this.saledetailid = saledetailid;
    }

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno;
    }

    public BigDecimal getWeightbalance() {
        return weightbalance;
    }

    public void setWeightbalance(BigDecimal weightbalance) {
        this.weightbalance = weightbalance;
    }

    public Integer getNumbalance() {
        return numbalance;
    }

    public void setNumbalance(Integer numbalance) {
        this.numbalance = numbalance;
    }

    public Integer getStockid() {
        return stockid;
    }

    public void setStockid(Integer stockid) {
        this.stockid = stockid;
    }
}
