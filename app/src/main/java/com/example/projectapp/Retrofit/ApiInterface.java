package com.example.projectapp.Retrofit;

import com.example.projectapp.Model.Category;
import com.example.projectapp.Model.Project;
import com.example.projectapp.Model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {


    @GET("/api/user/projects/recent-active")
    Call<List<Project>> getRecentActiveProjects(@Header("Authorization") String token);
    @GET("/api/categories/all")
    Call<List<Category>> getCategories(@Header("Authorization") String token);
    @POST("api/auth/signup")
    Call<ResponseBody> registerUser(@Body User user);



    @FormUrlEncoded
    @POST("api/auth/login")
    Call<ResponseBody> loginUser(@Field("email") String email, @Field("password") String password);

}
