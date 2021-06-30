package com.example.safeyourfamily.presentation.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.safeyourfamily.R;
import com.example.safeyourfamily.data.DataObserved;
import com.example.safeyourfamily.data.Observed;
import com.example.safeyourfamily.data.SignupInfo;
import com.example.safeyourfamily.db.AuthInfoPersist;
import com.example.safeyourfamily.network.FamilyService;
import com.example.safeyourfamily.network.RetrofitClient;
import com.example.safeyourfamily.presentation.main.MainActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthFragment extends Fragment {

    public TextView textView;
    public EditText editTextLogin;
    public EditText editTextPassword;
    public Button updateButton;
    public RetrofitClient retrofitClient;
    public AuthInfoPersist authInfoPersist;
    public FamilyService familyService;

    private HashMap<String, String> body = new HashMap<>();

    private HashMap<String, String> body_2 = new HashMap<>();

    SharedPreferences preferences;

    String login;
    String password;


    Callback<DataObserved> callbackDataObserved = new Callback<DataObserved>() {

        @Override
        public void onResponse(Call<DataObserved> call, Response<DataObserved> response) {
            if(response.body()!=null){
                preferences.edit()
                        .putString("DATA_OBSERVED" , response.body().toJsonString())
                        .apply();
                ((MainActivity) requireActivity()).goToMain();
            }
            else {
                Toast.makeText(getActivity(), "Ошибка в получении данных, обратитесь к администратору!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<DataObserved> call, Throwable t) {

        }
    };

    Callback<Observed> callbackObserved = new Callback<Observed>() {

        @Override
        public void onResponse(Call<Observed> call, Response<Observed> response) {
            if (response.body() != null) {
                preferences.edit()
                        .putString("OBSERVED", response.body().toString())
                        .apply();
                familyService.dataObserved(body).enqueue(callbackDataObserved);
            } else {
                Toast.makeText(getActivity(), "Ошибка в получении данных, обратитесь к вдминистратору!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Observed> call, Throwable t) {

        }
    };

    Callback<SignupInfo> callbackAuth = new Callback<SignupInfo>() {

        @SuppressLint("CommitPrefEdits")
        @Override
        public void onResponse(Call<SignupInfo> call, Response<SignupInfo> response) {
            if (response.body() != null) {
                preferences.edit().putString("login", login).apply();;
                preferences.edit().putString("password", password).apply();;
                preferences.edit()
                        .putString("SIGNUP_INFO", response.body().toString())
                        .apply();
                //((MainActivity) requireActivity()).goToMain();
                familyService.getInfoObserved(body).enqueue(callbackObserved);
            } else {
                Toast.makeText(getActivity(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<SignupInfo> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_screen, container, false);
        initializeSingletons();
        familyService = retrofitClient.getService();
        preferences = requireActivity().getSharedPreferences("AuthInfo", Context.MODE_PRIVATE);
        return view;
    }

    private void initializeSingletons() {
        retrofitClient = RetrofitClient.getInstance();
        authInfoPersist = AuthInfoPersist.getInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextLogin = view.findViewById(R.id.editTextLogin);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        updateButton = view.findViewById(R.id.signInButton);
        textView = view.findViewById(R.id.authInfo);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = editTextLogin.getText().toString();
                password = editTextPassword.getText().toString();

                body.put("login", login);
                body.put("password", password);

                if (!body.isEmpty()) {
                    familyService.checkAuth(body).enqueue(callbackAuth);
                } else {
                    Toast.makeText(getActivity(), "Введите данные для входа", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
