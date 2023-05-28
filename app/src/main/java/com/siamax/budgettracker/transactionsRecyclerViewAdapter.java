package com.siamax.budgettracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class transactionsRecyclerViewAdapter extends
        RecyclerView.Adapter<transactionsRecyclerViewAdapter.myViewHolder> {

    Context context;
    ArrayList<transactions> transactionsList;

    public transactionsRecyclerViewAdapter(Context context, ArrayList<transactions> transactionsList){
        this.context = context;
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public transactionsRecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                           int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);

        return new transactionsRecyclerViewAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull transactionsRecyclerViewAdapter.myViewHolder holder,
                                 int position) {

        holder.label.setText(transactionsList.get(position).getLabel());
        holder.amount.setText(transactionsList.get(position).getAmount()+"$");
        if(transactionsList.get(position).getAmount()>=0){
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green));
        }else{
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, updateTransaction.class);
                intent.putExtra("id",transactionsList.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        TextView amount;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.label);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
