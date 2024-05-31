package services;

import com.google.gson.Gson;
import model.CurrencyRateTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CurrencyService {
    private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";

    public CurrencyRateTable getCurrencyRateTable(String currencyCode, String date) throws Exception {
            String ulrStr = API_URL + currencyCode + "/" + date + "/?format=json";
            URL url = new URL(ulrStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                content.append(line);
            }
            rd.close();
            conn.disconnect();
            Gson gson = new Gson();
            CurrencyRateTable table = gson.fromJson(content.toString(), CurrencyRateTable.class);
            FileService.appendResponse(table.getCode() + ".txt",
                    content.toString());
            return table;
    }

    public CurrencyRateTable getCurrencyRateTable(String currencyCode) throws Exception
    {
        String content = Files.readString(Paths.get(currencyCode+".txt"));
        Gson gson = new Gson();
        CurrencyRateTable table = gson.fromJson(content.toString(), CurrencyRateTable.class);
        return table;
    }
}
