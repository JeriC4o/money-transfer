package com.yetanotherbank.api.core.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class TransactionData implements Serializable {
    private UUID id;
    private UUID userId;
    @JsonManagedReference
    private Set<TransactionLog> transactionLogs = new HashSet<>();

    public TransactionData() {
    }

    public TransactionData(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Set<TransactionLog> getTransactionLogs() {
        return transactionLogs;
    }

    public void addTransactionLog(TransactionLog log) {
        transactionLogs.add(log);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionData that = (TransactionData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
