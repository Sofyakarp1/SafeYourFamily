package com.example.safeyourfamily.presentation.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.safeyourfamily.R;
import com.example.safeyourfamily.data.PersonInfo;
import com.example.safeyourfamily.db.AuthInfoPersist;
import com.example.safeyourfamily.network.FamilyService;
import com.example.safeyourfamily.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private TextView name;
    private RetrofitClient retrofitClient;
    private FamilyService familyService;

    Callback<PersonInfo> callback = new Callback<PersonInfo>() {
        @Override
        public void onResponse(Call<PersonInfo> call, Response<PersonInfo> response) {
            name.setText(response.body().botEnabled);
        }

        @Override
        public void onFailure(Call<PersonInfo> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        retrofitClient = RetrofitClient.getInstance();
        familyService = retrofitClient.getService();
        Log.i("TAG", "onViewCreated: sdf");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.person_name);
        //name.setText("Sveta");
        familyService.getFamilyInfo().enqueue(callback);
        Toast.makeText(requireActivity(), AuthInfoPersist.getInstance().loginInfo, Toast.LENGTH_SHORT).show();

    }
}
