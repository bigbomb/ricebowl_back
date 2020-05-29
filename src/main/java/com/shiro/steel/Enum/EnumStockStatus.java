package com.shiro.steel.Enum;

public enum EnumStockStatus {

    INSTOCK(1, "在库"),
    OUTSTOCKFINISH(2, "已出库"),
    OUTSTOCKING(3,"出库中"),
    LOCKSTOCK(4,"已锁货"),
	TRANSPORTING(5,"运输中"),
	PROCESSING(6,"加工中"),
	PROCESSFINISH(7,"加工完成"),
    TRANSPORTFINISH(8,"运输完成");
    private final int value;
    private final String text;

    EnumStockStatus(int value, String text) {
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
