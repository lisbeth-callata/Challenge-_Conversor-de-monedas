    package com.xyzbank.utils;

    import com.google.gson.JsonObject;
    import com.google.gson.JsonParser;

    import java.net.URI;
    import java.net.http.HttpClient;
    import java.net.http.HttpRequest;
    import java.net.http.HttpResponse;

    public class ExchangeRateAPI {

        private static final String API_URL = "https://v6.exchangerate-api.com/v6/7b68de20d994ad3de3dfea5b/latest/USD";
        private JsonObject conversionRates;

        public ExchangeRateAPI() {
            getRates();
        }

        // Método para obtener las tasas de cambio de la API
        private void getRates() {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(API_URL))
                        .header("Accept", "application/json")
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String responseBody = response.body();
                    JsonObject responseJson = JsonParser.parseString(responseBody).getAsJsonObject();
                    this.conversionRates = responseJson.getAsJsonObject("conversion_rates");
                } else {
                    System.out.println("Error: " + response.statusCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Obtener la tasa de cambio para una moneda dada
        public double getExchangeRate(String currencyCode) {
            try {
                return conversionRates.get(currencyCode).getAsDouble();
            } catch (Exception e) {
                return -1;
            }
        }
    }
