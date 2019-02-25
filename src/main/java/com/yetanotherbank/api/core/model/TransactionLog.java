package com.yetanotherbank.api.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class TransactionLog implements Serializable {

    @JsonBackReference
    private TransactionData transaction;
    private UUID accountId;
    private BigDecimal amount;

    public TransactionLog() {
    }

    public TransactionLog(TransactionData transaction, UUID accountId, BigDecimal amount) {
        this.transaction = transaction;
        this.accountId = accountId;
        this.amount = amount;
    }

    public TransactionData getTransaction() {
        return transaction;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionLog that = (TransactionLog) o;
        return Objects.equals(transaction, that.transaction) &&
                Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction, accountId);
    }
}
