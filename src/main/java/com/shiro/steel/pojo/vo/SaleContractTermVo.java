package com.shiro.steel.pojo.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotations.TableName;

public class SaleContractTermVo  implements Serializable {
    private Integer id;

    private String memberid;
    @NotEmpty(message = "内容不能为空")
    private String content;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}