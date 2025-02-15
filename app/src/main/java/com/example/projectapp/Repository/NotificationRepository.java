package com.example.projectapp.Repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.projectapp.Model.Notification;
import com.example.projectapp.Retrofit.ApiClient;
import com.example.projectapp.Retrofit.ApiInterface;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepository {

    private ApiInterface apiInterface;
    private final Context context;

    public NotificationRepository(Context context) {
        this.context = context.getApplicationContext();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public interface GetNotificationsCallback {
        void onSuccess(List<Notification> notifications);
        void onFailure(String errorMessage);
    }

    public void getNotifications(GetNotificationsCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Corrected API call to get notifications
        Call<List<Notification>> call;
        if (token != null) {
            call = apiInterface.getNotifications("Bearer " + token);
        } else {
            // Handle null token case appropriately (maybe show an error or try to fetch public notifications)
            call = apiInterface.getNotifications("Bearer " + token);
        }

        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
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
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Log.e("Network Error", t.getMessage(), t);
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    public interface DeleteNotificationCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public void deleteNotification(int notificationId, final DeleteNotificationCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        // Assuming your delete endpoint is: DELETE /api/notifications/delete/{notificationId}
        Call<ResponseBody> call = apiInterface.deleteNotification("Bearer " + token, notificationId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String msg = response.body().string();
                        callback.onSuccess(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response");
                    }
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}

