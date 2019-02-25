package com.yetanotherbank.api.client;

import com.yetanotherbank.api.core.model.TransactionData;
import com.yetanotherbank.api.web.model.AccountBalanceRequest;
import com.yetanotherbank.api.web.model.DefaultResponse;
import com.yetanotherbank.api.web.model.TransferRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TransactionServiceApi {

    @POST("transaction/deposit")
    Call<DefaultResponse<TransactionData>> depositMoney(@Body AccountBalanceRequest balanceRequest);

    @POST("transaction/withdraw")
    Call<DefaultResponse<TransactionData>> withdrawMoney(@Body AccountBalanceRequest balanceRequest);

    @POST("transaction/transfer")
    Call<DefaultResponse<TransactionData>> transferMoney(@Body TransferRequest balanceRequest);
}
