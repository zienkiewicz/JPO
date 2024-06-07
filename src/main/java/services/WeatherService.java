package services;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import model.*;

public class WeatherService {
    private final String apiKey;
    private final String city;
    private static final String URL = "http://api.weatherapi.com/v1/current.json";

    public String getApiKey() {return apiKey;}
    public String getCity() {return city;}

    public WeatherService() throws Exception
    {
        apiKey = FileService.getProperty("weather_api_key");
        city = FileService.getProperty("weather_city");
    }

    public WeatherData getCurrentWeather() throws Exception {

            String urlStr = URL + "?key=" + apiKey + "&q=" + city;
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                content.append(line);
            }
            rd.close();
            con.disconnect();

            Gson gson = new Gson();
            Weather weather = gson.fromJson(content.toString(), Weather.class);
            String dateTime = weather.getLocation().getLocaltime();
            double temperature = weather.getCurrent().getTemp_c();
            String condition = weather.getCurrent().getCondition().getText();

            System.out.println(dateTime);
            System.out.println(temperature);
            System.out.println(condition);

            return new WeatherData(dateTime, temperature, condition);

    }
}
