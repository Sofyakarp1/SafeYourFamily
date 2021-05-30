package com.example.safeyourfamily.presentation.result;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.safeyourfamily.R;
import com.example.safeyourfamily.data.DataObserved;
import com.example.safeyourfamily.data.Observed;
import com.example.safeyourfamily.data.SignupInfo;
import com.example.safeyourfamily.network.FamilyService;
import com.example.safeyourfamily.network.RetrofitClient;

public class ResultFragment extends Fragment {

    private RetrofitClient retrofitClient;
    private FamilyService familyService;

    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        retrofitClient = RetrofitClient.getInstance();
        familyService = retrofitClient.getService();
        preferences = requireActivity().getSharedPreferences("AuthInfo", Context.MODE_PRIVATE);
        Log.i("TAG", "onViewCreated: sdf");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO отобразить имя в observedUserName
//        namePerson = view.findViewById(R.id.person_name);
//        nameObserved = view.findViewById(R.id.main_info);
//
//        value_action = view.findViewById(R.id.value_action);
//        value_time_action = view.findViewById(R.id.value_time_action);
//        value_rele = view.findViewById(R.id.value_rele);
//        value_time_rele = view.findViewById(R.id.value_time_rele);
//
//        SignupInfo userInfo = getSignupInfo(preferences.getString("SIGNUP_INFO", " "));
//        Observed observed = getObserved(preferences.getString("OBSERVED", " "));
//        String str = preferences.getString("DATA_OBSERVED", " ");
//        DataObserved dataObserved = getDataObserved(preferences.getString("DATA_OBSERVED", " "));
//
//        value_action.setText(dataObserved.sensor_action);
//        value_rele.setText(dataObserved.sensor_rele);
//        value_time_action.setText(dataObserved.time_action);
//        value_time_rele.setText(dataObserved.time_rele);
//
//
//        namePerson.setText("Привет, " + userInfo.name + "!");
//        nameObserved.setText("Информация об активности, " + observed.getName());
    }



}
