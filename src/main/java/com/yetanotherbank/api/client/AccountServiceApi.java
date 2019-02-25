package com.yetanotherbank.api.client;

import com.yetanotherbank.api.web.model.DefaultCollectionResponse;
import com.yetanotherbank.api.web.model.DefaultResponse;
import org.jooq.generated.public_.tables.pojos.MtAccount;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.UUID;

public interface AccountServiceApi {

    @GET("accounts/")
    Call<DefaultCollectionResponse<MtAccount>> getAllAccounts();

    @GET("account/{id}")
    Call<DefaultResponse<MtAccount>> getAccountById(@Path("id") UUID id);

    @PUT("account/")
    Call<DefaultResponse<MtAccount>> addAccount(@Body MtAccount user);

    @POST("account/")
    Call<DefaultResponse<MtAccount>> updateAccount(@Body MtAccount user);

    @DELETE("account/{id}")
    Call<DefaultResponse<UUID>> deleteAccount(@Path("id") UUID id);
}
