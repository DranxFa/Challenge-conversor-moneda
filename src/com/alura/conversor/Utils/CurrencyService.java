package com.alura.conversor.Utils;

import com.alura.conversor.Model.Moneda;
import com.alura.conversor.Model.ExchangeRateResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class CurrencyService {

    private static final Gson GSON = new Gson();
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String API_KEY = loadApiKey();

    private static String loadApiKey(){
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("config.properties")));
            String key = props.getProperty("exchange.api.key");

            if (key==null || key.isBlank()){
                throw new IllegalStateException("API key vacía");
            }

            return key;
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la API key", e);
        }
    }

    public double convert(Moneda origen, Moneda destino, double valor) {

        var URL = STR."https://v6.exchangerate-api.com/v6/\{API_KEY}/pair/\{origen}/\{destino}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = CLIENT
                    .send(request, HttpResponse.BodyHandlers.ofString());

            ExchangeRateResponse r = GSON.fromJson(response.body(), ExchangeRateResponse.class);
            return valor * r.conversionRate();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al convertir moneda", e);
        }
    }
}
