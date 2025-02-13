package com.example.projectapp.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.projectapp.Model.Project;
import com.example.projectapp.Retrofit.ApiClient;
import com.example.projectapp.Retrofit.ApiInterface;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {

    private final ApiInterface apiInterface;
    private final Context context;

    // Modify the constructor to accept context
    public ProjectRepository(Context context) {
        // Use application context to avoid leaks
        this.context = context.getApplicationContext();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    // Callback interface for returning data
    public interface GetProjectsCallback {
        void onSuccess(List<Project> projectList);
        void onFailure(String errorMessage);
    }

    // Fetch recent active projects
    public void getRecentActiveProjects(GetProjectsCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Make the API call, adding the token if it's available
        Call<List<Project>> call;
        if (token != null) {
            call = apiInterface.getRecentActiveProjects("Bearer " + token);
        } else {
            // You might want to handle the null token case differently,
            // but for now, we'll still call the API.
            call = apiInterface.getRecentActiveProjects("Bearer " + token);
        }

        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = "Error: empty response or server error.";
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", errorBody);
                            errorMessage = "Error: " + errorBody;
                        } catch (IOException e) {
                            Log.e("API Error", "Error reading error body", e);
                        }
                    }
                    Log.e("API Error", "Response Code: " + response.code());
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Log.e("Network Error", t.getMessage(), t);
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}