package com.siamax.budgettracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class addTransaction extends AppCompatActivity {

    ImageButton closeBtn;
    Button addTransactionBtn;
    TextInputLayout labelLayout;
    TextInputLayout amountLayout;
    TextInputLayout descriptionLayout;
    TextInputEditText labelInput;
    TextInputEditText amountInput;
    TextInputEditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction);

        addTransactionBtn = (Button) findViewById(R.id.addTransactionBtn);

        labelLayout = (TextInputLayout) findViewById(R.id.labelLayout);
        amountLayout = (TextInputLayout) findViewById(R.id.amountLayout);
        descriptionLayout = (TextInputLayout) findViewById(R.id.descriptionLayout);

        labelInput = (TextInputEditText) findViewById(R.id.labelInput);
        amountInput = (TextInputEditText) findViewById(R.id.amountInput);
        descriptionInput = (TextInputEditText) findViewById(R.id.descriptionInput);


        labelInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!labelInput.equals("")){
                    labelLayout.setError(null);
                }
            }
        });
        amountInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!amountInput.equals("")){
                    amountLayout.setError(null);
                }
            }
        });

        addTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lblInpt = labelInput.getText().toString();
                String amtInpt = amountInput.getText().toString();
                String dscInpt = descriptionInput.getText().toString();

                if(lblInpt.isEmpty()){
                    labelLayout.setError("Field can't be empty");
                }
                if(amtInpt.isEmpty()){
                    amountLayout.setError("Field can't be empty");
                }

                if(!lblInpt.isEmpty() && !amtInpt.isEmpty() && dscInpt.isEmpty()){

                    database myDB = new database(addTransaction.this);

                    myDB.addTransaction(myDB.getUserIDForAK(), lblInpt, Double.parseDouble(amtInpt),
                            "");

                }
                else if(!lblInpt.isEmpty() && !amtInpt.isEmpty() && !dscInpt.isEmpty()){

                    database myDB = new database(addTransaction.this);
                    myDB.addTransaction(myDB.getUserIDForAK(), lblInpt, Double.parseDouble(amtInpt),
                            dscInpt);

                }

            }
        });

        closeBtn = (ImageButton) findViewById(R.id.closeBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addTransaction.this, dashboard.class);
                startActivity(intent);
                finish();
            }
        });

    }
}