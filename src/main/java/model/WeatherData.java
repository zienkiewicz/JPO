package model;

public class WeatherData {
    private String dateTime;
    private double temperatue;
    private String condition;

    public WeatherData(String dateTime, double temperatue, String condition) {
        this.dateTime = dateTime;
        this.temperatue = temperatue;
        this.condition = condition;
    }

    public String getDateTime() {
        return dateTime;
    }

    public double getTemperatue() {
        return temperatue;
    }

    public String getCondition() {
        return condition;
    }
}
