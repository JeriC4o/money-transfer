package com.yetanotherbank.api.core.model;

import com.neovisionaries.i18n.CurrencyCode;

import java.io.Serializable;
import java.util.Objects;

public class CurrencyData implements Serializable {
    private String code;
    private String sign;

    public CurrencyData() {
    }

    public CurrencyData(CurrencyCode currency) {
        this.code = currency.getCurrency().getCurrencyCode();
        this.sign = currency.getCurrency().getSymbol();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyData currency = (CurrencyData) o;
        return Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
