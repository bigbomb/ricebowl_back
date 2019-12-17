package com.shiro.steel.pojo.vo;

import org.hibernate.validator.constraints.NotEmpty;

/** 

* @author 作者 lujunjie: 

* @version 创建时间：Dec 16, 2019 9:45:13 PM 

* 类说明 

*/
public class ProcessDetailFinishVo {
	@NotEmpty(message = "加工单不能为空")
	String processno;
	@NotEmpty(message = "加工明细不能为空")
	String jgdetail;

	String delids;
	public String getProcessno() {
		return processno;
	}

	public void setProcessno(String processno) {
		this.processno = processno;
	}

	public String getJgdetail() {
		return jgdetail;
	}

	public void setJgdetail(String jgdetail) {
		this.jgdetail = jgdetail;
	}

	public String getDelids() {
		return delids;
	}

	public void setDelids(String delids) {
		this.delids = delids;
	}
	
	
}
