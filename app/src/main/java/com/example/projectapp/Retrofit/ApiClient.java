package com.example.projectapp.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient() // Allow lenient parsing
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.59:8000/")  // Use 10.0.2.2 for localhost on emulator
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}

