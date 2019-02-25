package com.yetanotherbank.api.core.service;

import com.yetanotherbank.api.core.model.TransactionData;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionService {
    TransactionData deposit(UUID account, BigDecimal amount);
    TransactionData withdraw(UUID account, BigDecimal amount);
    TransactionData transferMoney(UUID accountOwnerFrom, UUID accountTo, BigDecimal amount);
}
