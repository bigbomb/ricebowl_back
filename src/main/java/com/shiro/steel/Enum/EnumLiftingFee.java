package com.shiro.steel.Enum;

public enum EnumLiftingFee {

	TAXFREE(1, false),
	TAXINCLUDED(2, false),
	TOTALTAXFREE(3,true),
	TOTALTAXINCLUDED(4,true);
    private final int value;
    private final Boolean status;

    EnumLiftingFee(int value, Boolean status) {
        this.value = value;
        this.status = status;
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
    public Boolean getStatus() {
        return this.status;
    }
}
