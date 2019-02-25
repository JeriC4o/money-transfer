package com.yetanotherbank.api.test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.yetanotherbank.api.web.model.DefaultCollectionResponse;
import com.yetanotherbank.api.web.model.DefaultResponse;
import org.junit.Assert;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public abstract class ApiClientBasedTest<T> {

    protected Class<T> serviceClass;

    public ApiClientBasedTest(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }

    protected T clientService() {
        return clientService(serviceClass);
    }

    protected <S> S clientService(Class<S> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:9999/")
                .addConverterFactory(JacksonConverterFactory
                        .create(new ObjectMapper()
                                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                                .setDateFormat(new StdDateFormat())))
                .build();
        return retrofit.create(serviceClass);
    }

    protected <T extends Serializable> T extractBody(Call<DefaultResponse<T>> call) throws IOException {
        DefaultResponse<T> resp = call.execute().body();

        Assert.assertNotNull(resp);

        return resp.getData();
    }

    protected <T extends Serializable> Collection<T> extractCollectionBody(Call<DefaultCollectionResponse<T>> call) throws IOException {
        DefaultCollectionResponse<T> resp = call.execute().body();

        Assert.assertNotNull(resp);

        return resp.getData();
    }
}
