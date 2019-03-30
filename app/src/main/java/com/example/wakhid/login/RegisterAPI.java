package com.example.wakhid.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/insert_user.php")
    Call<Value> daftar(@Field("username") String username,
                       @Field("nama") String nama,
                       @Field("password") String password,
                       @Field("password2") String password2);

    @FormUrlEncoded
    @POST("/masok_user.php")
    Call<Value> masok(@Field("username") String username,
                       @Field("password") String password);
}
