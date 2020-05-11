package com.example.optmiddlevalue;

public class Value2 {

    private int strike_price;
    private double call_final_price;
    private int call_open_interest;
    private double put_final_price;
    private int put_open_interest;

    public Value2(int strike_price, double call_final_price, int call_open_interest, double put_final_price, int put_open_interest) {
        this.strike_price = strike_price;
        this.call_final_price = call_final_price;
        this.call_open_interest = call_open_interest;
        this.put_final_price = put_final_price;
        this.put_open_interest = put_open_interest;
    }

    public int getStrikePrice() {
        return strike_price;
    }

    public double getCallFinalPrice() {
        return call_final_price;
    }

    public int getCallOI() {
        return call_open_interest;
    }

    public double getPutFinalPrice() {
        return put_final_price;
    }

    public int getPutOI() {
        return put_open_interest;
    }
}