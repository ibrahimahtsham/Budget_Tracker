package com.siamax.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class updateTransaction extends AppCompatActivity {

    ImageButton closeBtn;
    Button updateTransactionBtn;
    TextInputLayout edit_labelLayout;
    TextInputLayout edit_amountLayout;
    TextInputLayout edit_descriptionLayout;
    TextInputEditText edit_labelInput;
    TextInputEditText edit_amountInput;
    TextInputEditText edit_descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_transaction);

        updateTransactionBtn = (Button) findViewById(R.id.updateTransactionBtn);

        edit_labelLayout = (TextInputLayout) findViewById(R.id.edit_labelLayout);
        edit_amountLayout = (TextInputLayout) findViewById(R.id.edit_amountLayout);
        edit_descriptionLayout = (TextInputLayout) findViewById(R.id.edit_descriptionLayout);

        edit_labelInput = (TextInputEditText) findViewById(R.id.edit_labelInput);
        edit_amountInput = (TextInputEditText) findViewById(R.id.edit_amountInput);
        edit_descriptionInput = (TextInputEditText) findViewById(R.id.edit_descriptionInput);

        edit_labelInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!edit_labelInput.equals("")){
                    edit_labelLayout.setError(null);
                }
            }
        });
        edit_amountInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!edit_amountInput.equals("")){
                    edit_amountLayout.setError(null);
                }
            }
        });

        database db = new database(updateTransaction.this);
        int id = getIntent().getIntExtra("id", 0);

        String label = db.getLabelForUpdate(id);
        Double amount = db.getAmountForUpdate(id);
        String description = db.getDescriptionForUpdate(id);

        edit_labelInput.setText(label);
        edit_amountInput.setText(amount.toString());
        edit_descriptionInput.setText(description);

        updateTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lblInpt = edit_labelInput.getText().toString();
                String amtInpt = edit_amountInput.getText().toString();

                if(lblInpt.isEmpty()){
                    edit_labelLayout.setError("Field can't be empty");
                }
                if(amtInpt.isEmpty()){
                    edit_amountLayout.setError("Field can't be empty");
                }

                if(!lblInpt.isEmpty() && !amtInpt.isEmpty()){

                    String lbl = String.valueOf(edit_labelInput.getText());
                    final String amt = String.valueOf(edit_amountInput.getText());
                    final String dsc = String.valueOf(edit_descriptionInput.getText());

                    db.updateTransaction(id, lbl, Double.parseDouble(amt), dsc);

                }

            }
        });

        closeBtn = (ImageButton) findViewById(R.id.edit_closeBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateTransaction.this, dashboard.class);
                startActivity(intent);
                finish();
            }
        });

    }
}