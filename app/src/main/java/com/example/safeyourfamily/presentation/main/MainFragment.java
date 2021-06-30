package com.example.safeyourfamily.presentation.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.safeyourfamily.R;
import com.example.safeyourfamily.data.DataObserved;
import com.example.safeyourfamily.data.Observed;
import com.example.safeyourfamily.data.SignupInfo;
import com.example.safeyourfamily.network.FamilyService;
import com.example.safeyourfamily.network.RetrofitClient;
import com.example.safeyourfamily.data.SignupInfo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.safeyourfamily.R.id.value_action;

public class MainFragment extends Fragment {

    private TextView namePerson;
    private TextView nameObserved;
    private TextView value_action;
    private TextView value_time_action;
    private TextView value_rele;
    private TextView value_time_rele;
    private TextView result;


    private RetrofitClient retrofitClient;
    public Button updateButton;
    private FamilyService familyService;
    private HashMap<String, String> body = new HashMap<>();

    SharedPreferences preferences;

    Callback<DataObserved> callbackDataObservedSecond = new Callback<DataObserved>() {

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
                familyService.dataObserved(body).enqueue(callbackDataObservedSecond);
            } else {
                Toast.makeText(getActivity(), "Ошибка в получении данных, обратитесь к вдминистратору!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Observed> call, Throwable t) {

        }
    };

    Callback<DataObserved> callbackDataObserved = new Callback<DataObserved>() {

        @Override
        public void onResponse(Call<DataObserved> call, Response<DataObserved> response) {
            if(response.body()!=null){
                preferences.edit()
                        .putString("DATA_OBSERVED", response.body().toJsonString())
                        .apply();
                if (!response.body().getResult().equals("Безопасно")){
                    ((MainActivity) requireActivity()).goToResult();
                }
                else {
                    familyService.getInfoObserved(body).enqueue(callbackObserved);
                }

            }
            else {
                Toast.makeText(getActivity(), "Ошибка в получении данных, обратитесь к администратору!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<DataObserved> call, Throwable t) {

        }
    };

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
        namePerson = view.findViewById(R.id.person_name);
        nameObserved = view.findViewById(R.id.main_info);

        value_action = view.findViewById(R.id.value_action);
        value_time_action = view.findViewById(R.id.value_time_action);
        value_rele = view.findViewById(R.id.value_rele);
        value_time_rele = view.findViewById(R.id.value_time_rele);
        result = view.findViewById(R.id.value_result);


        SignupInfo userInfo = getSignupInfo(preferences.getString("SIGNUP_INFO", " "));
        Observed observed = getObserved(preferences.getString("OBSERVED", " "));
        //String str = preferences.getString("DATA_OBSERVED", " ");
        DataObserved dataObserved = getDataObserved(preferences.getString("DATA_OBSERVED", " "));

        value_action.setText(dataObserved.sensor_action);
        value_rele.setText(dataObserved.sensor_rele);
        value_time_action.setText(dataObserved.time_action);
        value_time_rele.setText(dataObserved.time_rele);
        result.setText(dataObserved.result);


        namePerson.setText("Привет, " + userInfo.name + "!");
        nameObserved.setText("Информация об активности, " + observed.getName());
        updateButton = view.findViewById(R.id.updateButton);

        body.put("login", preferences.getString("login", " "));
        body.put("password", preferences.getString("password", " "));

        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                familyService.dataObserved(body).enqueue(callbackDataObserved);
                System.out.println(preferences.getString("DATA_OBSERVED", " "));
                System.out.println(preferences.getString("OBSERVED", " "));
                System.out.println(preferences.getString("login", ""));
                System.out.println(preferences.getString("password", ""));
            }
        });

    }

    private SignupInfo getSignupInfo(String singupinfo) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(singupinfo);
            return new SignupInfo((Long) json.get("id"), json.get("phone").toString(), json.get("name").toString(), json.get("address").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Error when converting json to SignUpInfo");

    }

    private Observed getObserved(String observed) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(observed);
            return new Observed((Long) json.get("id"), json.get("phone").toString(), json.get("name").toString(), json.get("address").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Error when converting json to Observed");

    }

    private DataObserved getDataObserved(String data) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(data);
            return new DataObserved(json.get("name").toString(), json.get("sensor_action").toString(), json.get("sensor_rele").toString(), json.get("time_action").toString(), json.get("time_rele").toString(), json.get("result").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Error when converting json to DataObserved");
    }

}
