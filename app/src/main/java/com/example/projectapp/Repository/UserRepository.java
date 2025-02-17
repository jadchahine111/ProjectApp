package com.example.projectapp.Repository;

import android.content.Context;
import android.content.SharedPreferences;
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

    private final Context context ;

    public UserRepository(Context context) {
        this.context = context.getApplicationContext();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    // Method to register the user via the API
    // Register User with API call
    public void registerUser(User user, final RegisterUserCallback callback) {
        // Pass the user information to your API method
        Call<ResponseBody> call = apiInterface.registerUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Log.d("UserRepository", "Registration success: " + responseBody);
                        callback.onSuccess(responseBody);  // Pass the success response
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response body");
                    }
                } else {
                    String errorMessage = "Registration failed!";
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("UserRepository", "Error: " + errorBody);
                            callback.onFailure("Error: " + errorBody);  // Pass the error response
                        } catch (IOException e) {
                            e.printStackTrace();
                            callback.onFailure("Error parsing error body");
                        }
                    } else {
                        callback.onFailure(errorMessage);  // If no error body, return a general error
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("UserRepository", "Network failure: " + t.getMessage(), t);
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
    public void getUserDetails(final GetUserDetailsCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<User> call = apiInterface.getUserDetailsById("Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = "Error: empty response or server error.";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = "Error: " + response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("API Error", "Response Code: " + response.code());
                    callback.onFailure(errorMessage);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Network Error", t.getMessage(), t);
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    public void getOtherUserDetailsById(int id, final GetOtherUserDetailsCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<User> call = apiInterface.getOtherUserDetailsById("Bearer " + token, id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public interface GetOtherUserDetailsCallback {
        void onSuccess(User user);
        void onFailure(String error);
    }
    public void updateUserDetails(User user, final UpdateUserCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<User> call = apiInterface.updateUserDetails("Bearer " + token, user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = "Error: empty response or server error.";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = "Error: " + response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("API Error", "Response Code: " + response.code());
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Network Error", t.getMessage(), t);
                callback.onFailure("Network error: " + t.getMessage());
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
    public interface GetUserDetailsCallback {
        void onSuccess(User user);
        void onFailure(String error);
    }
    public interface UpdateUserCallback {
        void onSuccess(User updatedUser);
        void onFailure(String error);
    }
}
