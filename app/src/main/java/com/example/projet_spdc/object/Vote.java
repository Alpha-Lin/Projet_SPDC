package com.example.projet_spdc.object;

public class Vote {
    public Vote(String date, String intitutle, String position){
        this.date = date;
        this.intitutle = intitutle;
        this.position = position;
    }
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIntitutle() {
        return intitutle;
    }

    public void setIntitutle(String intitutle) {
        this.intitutle = intitutle;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    private String intitutle;
    private String position;
}
