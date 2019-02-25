package com.yetanotherbank.api.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountBalanceRequest implements Serializable {
    private UUID account;
    private BigDecimal amount;

    public AccountBalanceRequest() {
    }

    public AccountBalanceRequest(UUID account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    public UUID getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
