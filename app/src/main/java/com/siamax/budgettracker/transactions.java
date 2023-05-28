package com.siamax.budgettracker;

public class transactions {
    int id;
    String label;
    Double amount;
    String description;

    public transactions(int id, String label, Double amount, String description) {
        this.id = id;
        this.label = label;
        this.amount = amount;
        this.description = description;
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

    public String getDescription() { return description; }
}
