package com.shiro.steel.Enum;

public enum EnumContractStatus {
	  /**
     * 待支付
     */
    TOPAY(1001, "待支付"),
	
	PAID(1002, "已支付,进行流转"),
	
	WORKING(1003,"加工中"),
	
	DELIVERY(1004,"提货中"),
	
	TRANSPORT(1005,"提货中"),
	
	DONE(1006,"已完成");
	
	
	private final Integer value;
    private final String text;

    private EnumContractStatus(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 获取value
     */
    public Integer getValue() {
        return this.value;
    }

    /**
     * 获取Text
     */
    public String getText() {
        return this.text;
    }

}
