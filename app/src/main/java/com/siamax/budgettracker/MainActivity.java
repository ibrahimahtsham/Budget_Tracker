package com.siamax.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton  = (Button)findViewById(R.id.loginButton);
        signUpButton  = (Button)findViewById(R.id.signUpButton);

        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        if(!preferences.getString("userName", "").equals("")){
            signUpButton.setText("Reset");
        }

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), login.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), signUp.class);
                startActivity(intent);
            }
        });

    }
}