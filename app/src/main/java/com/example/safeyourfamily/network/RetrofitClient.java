package com.example.safeyourfamily.network;

import android.provider.SyncStateContract;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;

    private Retrofit retrofit;
    FamilyService familyService;

    public RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8443/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        familyService = retrofit.create(FamilyService.class);
    }

    public FamilyService getService(){
        return familyService;
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }

        return instance;
    }
}
