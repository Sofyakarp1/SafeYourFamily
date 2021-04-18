package com.example.safeyourfamily.db;

import com.example.safeyourfamily.network.RetrofitClient;

public class AuthInfoPersist {

    private static AuthInfoPersist instance = null;

    public String loginInfo = "";

    public static AuthInfoPersist getInstance() {
        if (instance == null) {
            instance = new AuthInfoPersist();
        }
        return instance;
    }
}
