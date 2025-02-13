package com.example.projectapp.Retrofit;
import com.example.projectapp.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/api/auth/signup")
    Call<User> registerUser(@Body User user);
}
