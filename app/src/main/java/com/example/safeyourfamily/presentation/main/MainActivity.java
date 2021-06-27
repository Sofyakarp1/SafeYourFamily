package com.example.safeyourfamily.presentation.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.safeyourfamily.R;
import com.example.safeyourfamily.presentation.auth.AuthFragment;
import com.example.safeyourfamily.presentation.result.ResultFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                //.add(R.id.main_container, new MainFragment(), "main_fragment")
                .add(R.id.main_container, new AuthFragment(), "auth_fragment")
                .addToBackStack(null)
                .commit();
    }

    public void goToMain() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_container, new MainFragment(), "main_fragment")
                .commit();
    }

    public void goToResult(){
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_container, new ResultFragment(), "result_fragment")
                .commit();
    }
}