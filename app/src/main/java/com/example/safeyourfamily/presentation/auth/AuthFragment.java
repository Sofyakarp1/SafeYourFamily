package com.example.safeyourfamily.presentation.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.safeyourfamily.data.AuthInfo;
import com.example.safeyourfamily.data.PersonInfo;
import com.example.safeyourfamily.data.SignupInfo;
import com.example.safeyourfamily.db.AuthInfoPersist;
import com.example.safeyourfamily.network.FamilyService;
import com.example.safeyourfamily.network.RetrofitClient;
import com.example.safeyourfamily.presentation.main.MainActivity;
import com.example.safeyourfamily.presentation.main.MainFragment;

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

    SharedPreferences preferences;

    Callback<SignupInfo> callback = new Callback<SignupInfo>() {

        @Override
        public void onResponse(Call<SignupInfo> call, Response<SignupInfo> response) {
            preferences.edit()
                    .putString("name", response.body().name)
                    .putString("login", body.get("login"))
                    .putString("password", body.get("password"))
                    .apply();
            ((MainActivity) requireActivity()).goToMain();
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
        updateButton = view.findViewById(R.id.updateButton);
        textView = view.findViewById(R.id.authInfo);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();

                body.put("login", login);
                body.put("password", password);

                if (!body.isEmpty()) {
                    familyService.checkAuth(body).enqueue(callback);
                } else {
                    Toast.makeText(getActivity(), "Введите данные для входа", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        view.findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((MainActivity)requireActivity()).goToMain();
//            }
//        });
        //familyService.getFamilyInfo().enqueue(callback);

    }

}
