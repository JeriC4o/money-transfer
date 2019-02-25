package com.yetanotherbank.api.client;

import com.yetanotherbank.api.core.model.CurrencyData;
import com.yetanotherbank.api.web.model.DefaultCollectionResponse;
import com.yetanotherbank.api.web.model.DefaultResponse;
import org.jooq.generated.public_.tables.pojos.MtCurrencyCourse;
import retrofit2.Call;
import retrofit2.http.*;

public interface CurrencyServiceApi {

    @GET("currencies")
    Call<DefaultCollectionResponse<CurrencyData>> getAllCurrencies();

    @GET("currency/{code}")
    Call<DefaultResponse<CurrencyData>> getCurrencyByCode(@Path("code") String code);

    @POST("currency-course/retrieve/")
    Call<DefaultResponse<MtCurrencyCourse>> getCurrencyCourse(@Body MtCurrencyCourse currencyCourseBase);

    @POST("currency-course/")
    Call<DefaultResponse<MtCurrencyCourse>> updateCurrencyCourse(@Body MtCurrencyCourse currencyCourseBase);

    @POST("currency-course/delete")
    Call<DefaultResponse<MtCurrencyCourse>> deleteCurrencyCourse(@Body MtCurrencyCourse currencyCourseBase);
}
