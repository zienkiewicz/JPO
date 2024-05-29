package model;

public class CurrencyRate {
    private String dateTime;
    private double rate;
    private String currency;

    public CurrencyRate(String date, double rate, String currency) {
        this.dateTime = date;
        this.rate = rate;
        this.currency = currency;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }
}
