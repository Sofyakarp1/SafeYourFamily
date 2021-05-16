package com.example.safeyourfamily.network;


import com.example.safeyourfamily.data.AuthInfo;
import com.example.safeyourfamily.data.PersonInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FamilyService {

    @GET("/getData")
    public Call<PersonInfo> getFamilyInfo();

    @POST("/send")
    public Call<AuthInfo> checkAuth();
}
