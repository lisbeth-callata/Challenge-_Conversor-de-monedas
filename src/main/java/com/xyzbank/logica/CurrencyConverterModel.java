package com.xyzbank.logica;

import com.xyzbank.utils.ExchangeRateAPI;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverterModel {

    private ExchangeRateAPI exchangeRateAPI;
    private Map<String, Double> cachedRates;

    public CurrencyConverterModel() {
        exchangeRateAPI = new ExchangeRateAPI();
        cachedRates = new HashMap<>();
    }

    // Método para realizar la conversión
    public double convert(String fromCurrency, String toCurrency, double amount) {
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }

        // Verificar si la tasa de cambio está en el caché
        String cacheKey = fromCurrency + "-" + toCurrency;
        if (!cachedRates.containsKey(cacheKey)) {
            double rate = Double.parseDouble(getExchangeRate(fromCurrency, toCurrency));
            cachedRates.put(cacheKey, rate);
        }

        double rate = cachedRates.get(cacheKey);
        return amount * rate;
    }

    // Método para obtener la tasa de cambio entre dos monedas
    public String getExchangeRate(String fromCurrency, String toCurrency) {
        double fromRate = exchangeRateAPI.getExchangeRate(fromCurrency);
        double toRate = exchangeRateAPI.getExchangeRate(toCurrency);

        if (fromRate != -1 && toRate != -1) {
            double exchangeRate = toRate / fromRate;
            return String.format("%.2f", exchangeRate);
        } else {
            return "Tasa no disponible";
        }
    }
}