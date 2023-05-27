package com.siamax.budgettracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class dashboard extends AppCompatActivity {

    TextView welcomeText;
    FloatingActionButton addBtn;

    TextView balance;
    TextView budget;
    TextView expense;

    database db;
    ArrayList<String> transaction_id, transaction_label, transaction_amount;


    ArrayList<transactions> transactionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        db = new database(dashboard.this);
        transaction_id = new ArrayList<>();
        transaction_label = new ArrayList<>();
        transaction_amount = new ArrayList<>();

        balance = (TextView) findViewById(R.id.balance);
        budget = (TextView) findViewById(R.id.budget);
        expense = (TextView) findViewById(R.id.expense);

        storeDataInArrays();

        Double Dbudget = 0.0;
        Double Dexpense = 0.0;
        Double Dbalance = 0.0;

        for(int i = 0; i < transaction_amount.size(); i++){
            Dbalance += Double.parseDouble(transaction_amount.get(i));
            if(Double.parseDouble(transaction_amount.get(i))>=0){
                Dbudget += Double.parseDouble(transaction_amount.get(i));
            }else{
                Dexpense += Double.parseDouble(transaction_amount.get(i));
            }
        }

        balance.setText(Dbalance.toString());
        budget.setText(Dbudget.toString());
        expense.setText(Dexpense.toString());

        RecyclerView recyclerView = findViewById(R.id.recycleview);

        setTransactionsList();

        transactionsRecyclerViewAdapter adapter = new transactionsRecyclerViewAdapter(this,
                transactionsList);
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
        for(int i = 0; i<transaction_label.size(); i++){
            transactionsList.add(new transactions(Integer.parseInt(transaction_id.get(i)),
                    transaction_label.get(i), Double.parseDouble(transaction_amount.get(i))));
        }
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllDataFromTransactionsTable();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                transaction_id.add(cursor.getString(0));
                transaction_label.add(cursor.getString(2));
                transaction_amount.add(cursor.getString(3));
            }
        }
    }

}
