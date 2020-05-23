package com.shiro.steel.service;

import java.math.BigDecimal;

@FunctionalInterface
public interface ToBigDecimalFunction<T> {
    BigDecimal applyAsBigDecimal(T value);
}
