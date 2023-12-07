package com.hedgeth.crawler.datasource;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.hedgeth.crawler.entity.CurrencyType;
import com.hedgeth.crawler.entity.TokenQuote;
import lombok.AllArgsConstructor;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;

public class CoinGeckoDataSource implements TokenDataSource {

    private static final String COINGECKO_API_URL = "https://api.coingecko.com/api/v3";

    private final HttpClient httpClient;
    private final Gson gson;
    private final String network;

    @Inject
    public CoinGeckoDataSource(HttpClient httpClient, Gson gson, @Named("COINGECKO_NETWORK") String network) {
        this.httpClient = httpClient;
        this.gson = gson;
        this.network = network;
    }

    @Override
    public List<TokenQuote> getHistoricQuotes(String tokenAddress, long from, long to, CurrencyType currencyType) {
        return null;
    }

    @Override
    public Optional<TokenQuote> getCurrentQuote(String tokenAddress, CurrencyType currencyType) {
        return Optional.empty();
    }
}
