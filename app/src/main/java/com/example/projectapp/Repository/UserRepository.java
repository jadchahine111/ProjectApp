package com.example.projectapp.Repository;

import android.util.Log;

import com.example.projectapp.Model.User;
import com.example.projectapp.Retrofit.ApiClient;
import com.example.projectapp.Retrofit.ApiInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
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
        Call<ResponseBody> call = apiInterface.registerUser(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Read the response body as a string to debug
                        String responseBody = response.body().string();
                        Log.e("Response", responseBody); // Log the response body
                        // You can optionally parse this response into your desired format (like User)
                        callback.onSuccess(responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response body");
                    }
                } else {
                    // Log more information to debug the failure
                    String errorMessage = "Registration failed!";
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("ErrorBody", errorBody); // Log the error body
                            callback.onFailure("Error: " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                            callback.onFailure("Error parsing error body");
                        }
                    } else {
                        callback.onFailure("Unknown error occurred");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle network failure
                Log.e("NetworkFailure", t.getMessage(), t);  // Log the network failure
                callback.onFailure("Network failure: " + t.getMessage());
            }
        });
    }



    // Method to login the user via the API
    public void loginUser(String email, String password, final LoginUserCallback callback) {
        Call<ResponseBody> call = apiInterface.loginUser(email, password); // Assuming the API expects email and password
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Read the response body as a string to debug
                        String responseBody = response.body().string();
                        Log.e("Response", responseBody); // Log the response body
                        // You can optionally parse this response into a User or token
                        callback.onSuccess(responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response body");
                    }
                } else {
                    // Log more information to debug the failure
                    String errorMessage = "Login failed!";
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("ErrorBody", errorBody); // Log the error body
                            callback.onFailure("Error: " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                            callback.onFailure("Error parsing error body");
                        }
                    } else {
                        callback.onFailure("Unknown error occurred");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle network failure
                Log.e("NetworkFailure", t.getMessage(), t);  // Log the network failure
                callback.onFailure("Network failure: " + t.getMessage());
            }
        });
    }














    // Callback interface for success and failure
    public interface RegisterUserCallback {
        void onSuccess(String responseBody);
        void onFailure(String error);
    }

    // Callback interface for success and failure for login
    public interface LoginUserCallback {
        void onSuccess(String responseBody);
        void onFailure(String error);
    }
}
