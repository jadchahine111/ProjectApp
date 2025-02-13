package com.example.projectapp.Repository;


import com.example.projectapp.Model.User;
import com.example.projectapp.Retrofit.ApiClient;
import com.example.projectapp.Retrofit.ApiInterface;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiInterface apiInterface;

    public UserRepository() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    // Method to register the user via the API
    public void registerUser(User user, final RegisterUserCallback callback) {
        Call<User> call = apiInterface.registerUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Successfully received a User object from the API response
                    callback.onSuccess(response.body());
                } else {
                    // If the response is not successful, handle the failure
                    String errorMessage = "Registration failed!";
                    if (response.errorBody() != null) {
                        try {
                            // If the API returned an error message, you can access it
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure("Error Code: " + response.code() + " - " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle network failure
                callback.onFailure("Network failure: " + t.getMessage());
            }
        });
    }

    // Callback interface for success and failure
    public interface RegisterUserCallback {
        void onSuccess(User user);
        void onFailure(String error);
    }
}
