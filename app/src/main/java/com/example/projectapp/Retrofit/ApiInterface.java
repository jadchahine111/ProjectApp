package com.example.projectapp.Retrofit;
import com.example.projectapp.Model.Category;
import com.example.projectapp.Model.Project;
import com.example.projectapp.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/api/auth/signup")
    Call<User> registerUser(@Body User user);
    @GET("/api/user/projects/recent-active")
    Call<List<Project>> getRecentActiveProjects();
    @GET("/api/user/categories")
    Call<List<Category>> getCategories();
}
