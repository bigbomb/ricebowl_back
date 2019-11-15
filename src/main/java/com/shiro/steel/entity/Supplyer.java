package com.shiro.steel.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("supplyer")
public class Supplyer implements Serializable {
	@TableId(type = IdType.AUTO)
    private Integer id;

    private String supplyername;

    private String supplyerphone;

    private String supplyeraddress;

    private String status;

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

    public String getSupplyername() {
        return supplyername;
    }

    public void setSupplyername(String supplyername) {
        this.supplyername = supplyername;
    }

    public String getSupplyerphone() {
        return supplyerphone;
    }

    public void setSupplyerphone(String supplyerphone) {
        this.supplyerphone = supplyerphone;
    }

    public String getSupplyeraddress() {
        return supplyeraddress;
    }

    public void setSupplyeraddress(String supplyeraddress) {
        this.supplyeraddress = supplyeraddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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