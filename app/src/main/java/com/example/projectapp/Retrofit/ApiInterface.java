package com.example.projectapp.Retrofit;

import com.example.projectapp.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("api/auth/signup")
    Call<ResponseBody> registerUser(@Body User user);

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<ResponseBody> loginUser(@Field("email") String email, @Field("password") String password);

}
