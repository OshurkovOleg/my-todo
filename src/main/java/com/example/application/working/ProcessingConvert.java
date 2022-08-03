package com.example.application.working;

import com.example.application.Settings;
import com.example.application.exceptions.ConverterException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProcessingConvert {

    private static final String ERROR_SENDING_REQUEST = "Error while sending GET request";
    private static final String REQUEST_FAILED = "\nRequest failed";

    public static String startConvert(String getMoney, String resultMoney, BigDecimal count) throws IOException {

        String apiUrl = String.format("https://api.apilayer.com/currency_data/convert?to=%s&from=%s&amount=%s", resultMoney, getMoney, count.toString());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(apiUrl))
                .headers("User-Agent", Settings.USER_AGENT, "apikey", Settings.API_KEY)
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException exp) {
            throw new ConverterException(ERROR_SENDING_REQUEST + exp);
        }


        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            JSONObject jsonObject = new JSONObject(response.body());
            return resultMoney + " = " + jsonObject.get(Settings.DESIRED_VALUE_FROM_API);

        } else {
            System.out.println(REQUEST_FAILED);
        }
        return null;
    }
}
