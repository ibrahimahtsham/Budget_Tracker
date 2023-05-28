package com.siamax.budgettracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    EditText userName;
    EditText password;
    Button loginButton;
    ImageButton closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);

        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        if(!preferences.getString("userName", "").equals("")){
            userName.setText(preferences.getString("userName", ""));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") ||  pass.equals("")){
                    Toast.makeText(login.this, "Username or Password can't be empty",
                            Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
                    String pUser = preferences.getString("userName", "");
                    String pPass = preferences.getString("password", "");

                    if (user.equals(pUser) && pass.equals(pPass)){
                        Intent intent = new Intent(login.this, dashboard.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(login.this, "Invalid Username or Password",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        closeBtn = (ImageButton) findViewById(R.id.login_closeBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
