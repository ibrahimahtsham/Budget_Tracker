package com.siamax.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

                String lbl = String.valueOf(edit_labelInput.getText());
                final String amt = String.valueOf(edit_amountInput.getText());
                final String dsc = String.valueOf(edit_descriptionInput.getText());

                db.updateTransaction(id, lbl, Double.parseDouble(amt), dsc);
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