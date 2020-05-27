package com.shiro.steel.pojo.dto;

import java.math.BigDecimal;

public class DeliveryOrderDetailPurDto {
    private BigDecimal actualweight;
    private Integer id;
    private Integer saledetailid;
    private String deliveryno;
    private BigDecimal balance;

    public BigDecimal getActualweight() {
        return actualweight;
    }

    public void setActualweight(BigDecimal actualweight) {
        this.actualweight = actualweight;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
