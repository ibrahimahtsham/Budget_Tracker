package com.siamax.budgettracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class dashboard extends AppCompatActivity {
    FloatingActionButton addBtn;

    Button logoutButton;

    TextView balance;
    TextView budget;
    TextView expense;

    database db;
    ArrayList<String> transaction_id, transaction_label, transaction_amount, transaction_description;

    transactionsRecyclerViewAdapter adapter;
    RecyclerView recyclerView;


    ArrayList<transactions> transactionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        logoutButton = (Button) findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, MainActivity.class);
                startActivity(intent);
            }
        });


        db = new database(dashboard.this);
        transaction_id = new ArrayList<>();
        transaction_label = new ArrayList<>();
        transaction_amount = new ArrayList<>();
        transaction_description = new ArrayList<>();

        balance = (TextView) findViewById(R.id.balance);
        budget = (TextView) findViewById(R.id.budget);
        expense = (TextView) findViewById(R.id.expense);

        storeDataInArrays();

        refreshBalance();

        recyclerView = findViewById(R.id.recycleview);

        setTransactionsList();

        adapter = new transactionsRecyclerViewAdapter(this,
                transactionsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        String user = preferences.getString("userName", "");

        logoutButton.setText("Logout " + user);

        addBtn = (FloatingActionButton) findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, addTransaction.class);
                startActivity(intent);
                finish();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    transactions deletedTransaction = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            deletedTransaction = transactionsList.get(position);

            deleteFromDB(position);
            transactionsList.remove(position);
            adapter.notifyItemRemoved(position);

            refreshBalance();


            Snackbar.make(recyclerView, deletedTransaction.getLabel() + " Deleted",
                            Snackbar.LENGTH_SHORT)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            transactionsList.add(position, deletedTransaction);
                            redoTransactionInDB(position);
                            adapter.notifyItemInserted(position);

                            refreshBalance();
                        }
                    }).setTextColor(Color.parseColor("#E53935"))
                    .setActionTextColor(Color.parseColor("#43A047")).show();

        }
    };

    private void setTransactionsList(){
        for(int i = 0; i<transaction_label.size(); i++){
            transactionsList.add(new transactions(Integer.parseInt(transaction_id.get(i)),
                    transaction_label.get(i), Double.parseDouble(transaction_amount.get(i)),
                    transaction_description.get(i)));
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
                transaction_description.add(cursor.getString(4));
            }
        }
    }

    void deleteFromDB(int position){

        database db = new database(dashboard.this);
        db.readAllDataFromTransactionsTable();

        int index = transactionsList.get(position).getId();

        db.deleteTransaction(index);

    }

    void redoTransactionInDB(int position){

        database db = new database(dashboard.this);
        db.redoTransaction(transactionsList.get(position).getLabel(),
                transactionsList.get(position).getAmount(),
                transactionsList.get(position).getDescription());
    }

    void refreshBalance(){
        Double Dbudget = 0.0;
        Double Dexpense = 0.0;
        Double Dbalance = 0.0;

        database db = new database(dashboard.this);
        Cursor rs = db.getAmountForRefresh();

        if (!(rs.getCount() == 0)){
            while(rs.moveToNext()){
                Dbalance += rs.getDouble(0);
                System.out.println(rs.getDouble(0));
                if(rs.getDouble(0)>=0){
                    Dbudget += rs.getDouble(0);
                }else{
                    Dexpense += rs.getDouble(0);
                }
            }
        }

        balance.setText(String.format("%.2f", Dbalance) + " PKR");
        budget.setText(String.format("%.2f", Dbudget) + " PKR");
        expense.setText(String.format("%.2f", Dexpense) + " PKR");

    }

}
