package com.siamax.budgettracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signUp extends AppCompatActivity {

    TextView labelView;
    EditText userName;
    EditText password;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        labelView = (TextView) findViewById(R.id.labelView);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        if(!preferences.getString("userName", "").equals("")){
            signUpButton.setText("Switch");
            labelView.setText("Switch User");
            userName.setHint("New Username");
            password.setHint("New Password");
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();

                SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
                String pUser = preferences.getString("userName", "");
                String pPass = preferences.getString("password", "");

                if (user.equals("") ||  pass.equals("")){
                    Toast.makeText(signUp.this, "Username or Password can't be empty", Toast.LENGTH_SHORT).show();
                }

                else if (pUser.equals(user)) {
                    Toast.makeText(signUp.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("userName", user);
                    editor.apply();
                    editor.putString("password", pass);
                    editor.apply();

                    database db = new database(signUp.this);

                    Intent intent = new Intent(signUp.this, dashboard.class);
                    startActivity(intent);
                    finish();

                    if (db.checkIfUserExistInDB()){
                        return;
                    }else{
                        db.addUser(user);
                    }



                }

            }
        });

    }

}
