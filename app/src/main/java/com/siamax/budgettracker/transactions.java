package com.siamax.budgettracker;

public class transactions {
    int id;
    String label;
    Double amount;

    public transactions(int id, String label, Double amount) {
        this.id = id;
        this.label = label;
        this.amount = amount;
    }

    public String getLabel() {
        return label;
    }

    public Double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }
}
