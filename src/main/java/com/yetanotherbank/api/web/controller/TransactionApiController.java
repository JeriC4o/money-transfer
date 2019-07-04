package com.yetanotherbank.api.web.controller;

import com.yetanotherbank.api.core.service.TransactionService;
import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.model.AccountBalanceRequest;
import com.yetanotherbank.api.web.model.DefaultResponse;
import com.yetanotherbank.api.web.model.TransferRequest;
import com.yetanotherbank.api.web.util.JsonWebUtils;
import spark.Service;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransactionApiController implements RouteConfiguration {

    private final JsonWebUtils jsonWebUtils;
    private final TransactionService transactionService;

    @Inject
    public TransactionApiController(JsonWebUtils jsonWebUtils,
                                    TransactionService transactionService) {
        this.jsonWebUtils = jsonWebUtils;
        this.transactionService = transactionService;
    }

    @Override
    public void configure(Service sparkService) {
        sparkService.path("/transaction", () -> {
            sparkService.post("/transfer", jsonWebUtils.response((req, res) -> {
                TransferRequest transfer = jsonWebUtils.extractFromBody(req, TransferRequest.class);

                return new DefaultResponse<>(transactionService.transferMoney(transfer.getAccountFrom(),
                        transfer.getAccountTo(), transfer.getAmount()));
            }));

            sparkService.post("/deposit", jsonWebUtils.response((req, res) -> {
                AccountBalanceRequest balanceRequest = jsonWebUtils.extractFromBody(req, AccountBalanceRequest.class);

                return new DefaultResponse<>(transactionService.deposit(balanceRequest.getAccount(),
                        balanceRequest.getAmount()));
            }));

            sparkService.post("/withdraw", jsonWebUtils.response((req, res) -> {
                AccountBalanceRequest balanceRequest = jsonWebUtils.extractFromBody(req, AccountBalanceRequest.class);

                return new DefaultResponse<>(transactionService.deposit(balanceRequest.getAccount(),
                        balanceRequest.getAmount().negate()));
            }));
        });
    }
}
