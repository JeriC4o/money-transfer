package com.yetanotherbank.api.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class TransferRequest implements Serializable {
    private UUID accountFrom;
    private UUID accountTo;
    private BigDecimal amount;

    public TransferRequest() {
    }

    public TransferRequest(UUID accountFrom, UUID accountTo, BigDecimal amount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public UUID getAccountFrom() {
        return accountFrom;
    }

    public UUID getAccountTo() {
        return accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
