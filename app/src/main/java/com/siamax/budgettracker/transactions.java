package com.siamax.budgettracker;

public class transactions {
    String label;
    Double amount;

    public transactions(String label, Double amount) {
        this.label = label;
        this.amount = amount;
    }

    public String getLabel() {
        return label;
    }

    public Double getAmount() {
        return amount;
    }
}
