package com.shiro.steel.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("sale_contract_warehouse")
public class SaleContractWarehouse implements Serializable {
	@TableId(type = IdType.AUTO)
    private Integer id;

    private String warehousename;

    private String warehouseaddress;

    private String warehousephone;

    private String warehousefax;

    private String memberid;

    private Date crt;

    private Date upt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getUpt() {
        return upt;
    }

    public void setUpt(Date upt) {
        this.upt = upt;
    }
}