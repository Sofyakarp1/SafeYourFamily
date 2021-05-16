package com.example.safeyourfamily.network;


import com.example.safeyourfamily.data.AuthInfo;
import com.example.safeyourfamily.data.PersonInfo;
import com.example.safeyourfamily.data.SignupInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FamilyService {

    @GET("/getData")
    public Call<PersonInfo> getFamilyInfo();

    @POST("/auth")
    public Call<SignupInfo> checkAuth(
            @Field("login") String login,
            @Field("password") String password
    );
}
