package com.example.projectapp.Repository;

import com.example.projectapp.Model.Project;
import com.example.projectapp.Retrofit.ApiClient;
import com.example.projectapp.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProjectRepository {

    // This is your Retrofit interface
    private ApiInterface apiInterface;

    public ProjectRepository() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    // Callback interface for returning data
    public interface GetProjectsCallback {
        void onSuccess(List<Project> projectList);
        void onFailure(String errorMessage);
    }

    // Fetch recent active projects
    public void getRecentActiveProjects(GetProjectsCallback callback) {
        apiInterface.getRecentActiveProjects().enqueue(new retrofit2.Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Error: empty response or server error.");
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}
