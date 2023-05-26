package com.siamax.budgettracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity {

    TextView welcomeText;
    FloatingActionButton addBtn;

    ArrayList<transactions> transactionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        RecyclerView recyclerView = findViewById(R.id.recycleview);

        setTransactionsList();

        transactionsRecyclerViewAdapter adapter = new transactionsRecyclerViewAdapter(this, transactionsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        String user = preferences.getString("userName", "");

        welcomeText = (TextView) findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome "+user+"!");

        addBtn = (FloatingActionButton) findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, addTransaction.class);
                startActivity(intent);
            }
        });

    }

    private void setTransactionsList(){
        String[] labels = {"Bananas", "Oranges", "Apples", "Bananas", "Oranges", "Apples", "Bananas", "Oranges", "Apples", "Bananas", "Oranges", "Apples"};
        Double[] amounts = {11.11, -222.22, 3333.33, -11.11, 222.22, -3333.33, 11.11, -222.22, 3333.33, 11.11, -222.22, 3333.33};

        for(int i = 0; i < labels.length; i++){
            transactionsList.add(new transactions(labels[i], amounts[i]));
        }
    }

}
