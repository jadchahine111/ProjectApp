package com.example.projectapp.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.projectapp.Model.Category;
import com.example.projectapp.Retrofit.ApiClient;
import com.example.projectapp.Retrofit.ApiInterface;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {

    private ApiInterface apiInterface;
    private final Context context;

    public CategoryRepository(Context context) {
        this.context = context.getApplicationContext();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public interface GetCategoriesCallback {
        void onSuccess(List<Category> categories);
        void onFailure(String errorMessage);
    }

    public void getCategories(GetCategoriesCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Corrected API call to get categories
        Call<List<Category>> call;
        if (token != null) {
            call = apiInterface.getCategories("Bearer " + token);
        } else {
            // Handle null token case appropriately (maybe show an error or try to fetch public categories)
            call = apiInterface.getCategories("Bearer " + token);
        }

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
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
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("Network Error", t.getMessage(), t);
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}
