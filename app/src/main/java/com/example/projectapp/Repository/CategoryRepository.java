package com.example.projectapp.Repository;

import com.example.projectapp.Model.Category;
import com.example.projectapp.Retrofit.ApiClient;
import com.example.projectapp.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {

    private ApiInterface apiInterface;

    public CategoryRepository() {
        // E.g., build Retrofit or get it from a singleton
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public interface GetCategoriesCallback {
        void onSuccess(List<Category> categories);
        void onFailure(String errorMessage);
    }

    public void getCategories(GetCategoriesCallback callback) {
        apiInterface.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                callback.onFailure("Network failure: " + t.getMessage());
            }
        });
    }
}
