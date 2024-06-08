package services;

import com.google.gson.Gson;
import model.CurrencyRateTable;
import model.CurrencyRate;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownServiceException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;

public class CurrencyService {
    private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";

    public CurrencyRateTable getCurrencyRateTable(String currencyCode, String date) throws Exception {
            String ulrStr = API_URL + currencyCode + "/" + date + "/?format=json";
            URL url = new URL(ulrStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            System.out.println(ulrStr);
            System.out.println(conn.getResponseCode());
            int responseCode = conn.getResponseCode();
            switch (responseCode)
            {
                case 200:
                    System.out.println("xdd");
                    break;
                case 400:
                    throw new CurrencyServiceBadRequestException("Bad request");
                case 404:
                    throw new CurrencyServiceNoDataFoundException("No data found for this time period");
                default:
                    throw new RuntimeException(String.format("Unknown response code %d", responseCode));
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                content.append(line);
            }
            rd.close();
            conn.disconnect();
            Gson gson = new Gson();
            CurrencyRateTable main;
            CurrencyRateTable table = gson.fromJson(content.toString(), CurrencyRateTable.class);
            main = getCurrencyRateTableNoExcept(table.getCode());
            if (main == null) {
                FileService.writeResponse(table.getCode() + ".txt", content.toString());
                return table;
            }
            for (CurrencyRate rate : table.getRates()) {
                main.appendRate(rate);
            }
            FileService.writeResponse(table.getCode() + ".txt" ,gson.toJson(main, CurrencyRateTable.class));
            return main;
    }
    // XDXDXDXD
    private CurrencyRateTable getCurrencyRateTableNoExcept(String currencyCode) {
        String path = String.format("%s.txt",currencyCode);
        String content;
        if(!new File(path).isFile())
        {
            return null;
        }
        try {
            content = Files.readString(Paths.get(path));
        } catch (Exception e) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(content.toString(), CurrencyRateTable.class);
    }

    public CurrencyRateTable getCurrencyRateTable(String currencyCode) throws Exception
    {
        CurrencyRateTable table = getCurrencyRateTableNoExcept(currencyCode);
        if(table == null)
        {
            throw new CurrencyServiceNoDataFoundException("No data found for specified currencyCode");
        }
        return table;
    }
}
