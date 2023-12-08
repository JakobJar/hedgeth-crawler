package com.hedgeth.crawler.datasource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.hedgeth.crawler.entity.CurrencyType;
import com.hedgeth.crawler.entity.TokenQuote;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class CoinGeckoDataSource implements APIDataSource {

    private static final String COINGECKO_API_URL = "https://api.coingecko.com/api/v3";

    private final HttpClient httpClient;
    private final Gson gson;
    private final String apiKey;
    private final String network;

    @Inject
    public CoinGeckoDataSource(HttpClient httpClient, Gson gson,
                               @Named("COINGECKO_API_KEY") String apiKey,
                               @Named("COINGECKO_NETWORK") String network) {
        this.httpClient = httpClient;
        this.gson = gson;
        this.apiKey = apiKey;
        this.network = network;
    }

    @Override
    public List<TokenQuote> getHistoricQuotes(String tokenAddress, long from, long to, CurrencyType currencyType) {
        var path = "/coins/" + this.network +
                "/contract/" + tokenAddress +
                "/market_chart/range?vs_currency=" + currencyType.getCoinGeckoName() +
                "&from=" + from +
                "&to=" + to;
        var request = createRequestBuilder(path).GET().build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
                return parseHistoricQuotes(response.body(), tokenAddress);
        } catch (IOException | InterruptedException e) {
            log.error("Error while getting historic quotes", e);
        }
        return Collections.emptyList();
    }

    private List<TokenQuote> parseHistoricQuotes(String rawJson, String tokenAddress) {
        var quotes = new LinkedList<TokenQuote>();
        var json = this.gson.fromJson(rawJson, JsonObject.class);
        json.getAsJsonArray("prices").forEach(rawPrice -> {
            var timestamp = rawPrice.getAsJsonArray().get(0).getAsLong();
            var price = rawPrice.getAsJsonArray().get(1).getAsBigDecimal();
            var quote = new TokenQuote(tokenAddress, timestamp, price);
            quotes.add(quote);
        });
        return quotes;
    }

    @Override
    public List<TokenQuote> getCurrentQuotes(List<String> tokenAddresses, CurrencyType currencyType) {
        var path = "/simple/token_price/" + this.network +
                "?token_addresses=" + String.join(",", tokenAddresses) +
                "&vs_currency=" + currencyType.getCoinGeckoName();
        var request = createRequestBuilder(path).GET().build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
                return parseCurrentQuotes(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Error while getting current quotes", e);
        }
        return Collections.emptyList();
    }

    private List<TokenQuote> parseCurrentQuotes(String rawJson) {
        var quotes = new LinkedList<TokenQuote>();
        var json = this.gson.fromJson(rawJson, JsonObject.class);
        json.entrySet().forEach(entry -> {
            var tokenAddress = entry.getKey();
            var price = entry.getValue().getAsJsonObject().get("usd").getAsBigDecimal();
            var quote = new TokenQuote(tokenAddress, System.currentTimeMillis(), price);
            quotes.add(quote);
        });
        return quotes;
    }

    private HttpRequest.Builder createRequestBuilder(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create(COINGECKO_API_URL + path))
                .header("Content-Type", "application/json");
    }
}
