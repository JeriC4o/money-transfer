package com.yetanotherbank.api.client;

import com.yetanotherbank.api.web.model.DefaultCollectionResponse;
import com.yetanotherbank.api.web.model.DefaultResponse;
import org.jooq.generated.public_.tables.pojos.MtUser;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.UUID;

public interface UserServiceApi {

    @GET("users/")
    Call<DefaultCollectionResponse<MtUser>> getAllUsers();

    @GET("user/{id}")
    Call<DefaultResponse<MtUser>> getUserById(@Path("id") UUID id);

    @GET("user/byEmail/{email}")
    Call<DefaultResponse<MtUser>> getUserByEmail(@Path("email") String email);

    @PUT("user/")
    Call<DefaultResponse<MtUser>> addUser(@Body MtUser user);

    @POST("user/")
    Call<DefaultResponse<MtUser>> updateUser(@Body MtUser user);

    @DELETE("user/{id}")
    Call<DefaultResponse<UUID>> deleteUser(@Path("id") UUID id);
}
