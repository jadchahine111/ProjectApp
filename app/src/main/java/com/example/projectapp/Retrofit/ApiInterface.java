package com.example.projectapp.Retrofit;

import com.example.projectapp.Model.Category;
import com.example.projectapp.Model.Notification;
import com.example.projectapp.Model.Project;
import com.example.projectapp.Model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {


    @GET("/api/user/projects/recent-active")
    Call<List<Project>> getRecentActiveProjects(@Header("Authorization") String token);
    @GET("/api/categories/all")
    Call<List<Category>> getCategories(@Header("Authorization") String token);
    @POST("/api/auth/signup")
    Call<ResponseBody> registerUser(@Body User user);

    @GET("/api/user/info")
    Call<User> getUserDetailsById(@Header("Authorization") String token);
    @PUT("api/user/update")
    Call<User> updateUserDetails(@Header("Authorization") String token, @Body User user);

    @GET("/api/notifications/all")
    Call<List<Notification>> getNotifications(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("/api/auth/login")
    Call<ResponseBody> loginUser(@Field("email") String email, @Field("password") String password);

    @POST("/api/user/add-project")
    Call<ResponseBody> addProject(@Header("Authorization") String token, @Body Project project);

    @GET("/api/projects/active-projects")
    Call<List<Project>> getUserActiveProjects(@Header("Authorization") String token);
    @GET("api/user/applied-projects")
    Call<List<Project>> getUserAppliedProjects(@Header("Authorization") String token);

    @GET("api/user/favorited-projects")
    Call<List<Project>> getFavoritedProjects(@Header("Authorization") String token);

    @GET("api/user/accepted-projects")
    Call<List<Project>> getAcceptedProjects(@Header("Authorization") String token);
    @GET("api/user/rejected-projects")
    Call<List<Project>> getRejectedProjects(@Header("Authorization") String token);

    @GET("api/user/archived-projects")
    Call<List<Project>> getUserArchivedProjects(@Header("Authorization") String token);
    @PUT("api/projects/unarchive/{projectId}")
    Call<ResponseBody> unarchiveProject(@Header("Authorization") String token, @Path("projectId") int projectId);
    @PUT("api/projects/archive/{projectId}")
    Call<ResponseBody> archiveProject(@Header("Authorization") String token, @Path("projectId") int projectId);
    @DELETE("api/notifications/delete/{notificationId}")
    Call<ResponseBody> deleteNotification(@Header("Authorization") String token, @Path("notificationId") int notificationId);
    @POST("api/add-fav/{projectId}")
    Call<ResponseBody> addProjectToFav(@Header("Authorization") String token, @Path("projectId") int projectId);
    @DELETE("api/remove-fav/{projectId}")
    Call<ResponseBody> remProjectFromFav(@Header("Authorization") String token, @Path("projectId") int projectId);


}
