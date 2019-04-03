package com.ashish.myteam;

public class transaction_data {
    long amount;
    String date;
    String transName;
    String transType;

    public transaction_data(long amount, String date, String transName, String transType) {
        this.amount = amount;
        this.date = date;
        this.transName = transName;
        this.transType = transType;
    }

    public long getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTransName() {
        return transName;
    }

    public String getTransType() {
        return transType;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}
