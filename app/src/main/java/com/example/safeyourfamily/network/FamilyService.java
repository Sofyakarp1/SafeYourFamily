package com.example.safeyourfamily.network;


import com.example.safeyourfamily.data.DataObserved;
import com.example.safeyourfamily.data.Observed;
import com.example.safeyourfamily.data.Person;
import com.example.safeyourfamily.data.SignupInfo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FamilyService {

    @GET("/getData")
    public Call<Person> getFamilyInfo();

    //@FormUrlEncoded
    @POST("/auth")
    public Call<SignupInfo> checkAuth(
            @Body HashMap<String, String> body
    );

    @POST("/dataObserved")
    public Call<Observed> getInfoObserved(
            @Body HashMap<String, String> body
    );

    @POST("/cloud/object")
    public Call<DataObserved> dataObserved(
            @Body HashMap<String, String> body
    );
}
