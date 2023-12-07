package com.hedgeth.crawler.datasource;

import com.hedgeth.crawler.entity.CurrencyType;
import com.hedgeth.crawler.entity.TokenQuote;

import java.util.List;
import java.util.Optional;

public interface TokenDataSource {

    CurrencyType DEFAULT_CURRENCY_TYPE = CurrencyType.USD;

    default List<TokenQuote> getHistoricQuotes(String tokenAddress, long from, long to) {
        return getHistoricQuotes(tokenAddress, from, to, DEFAULT_CURRENCY_TYPE);
    }

    List<TokenQuote> getHistoricQuotes(String tokenAddress, long from, long to, CurrencyType currencyType);

    default List<TokenQuote> getCurrentQuotes(List<String> tokenAddresses, String tokenAddress) {
        return getCurrentQuotes(tokenAddresses, DEFAULT_CURRENCY_TYPE);
    }

    List<TokenQuote> getCurrentQuotes(List<String> tokenAddress, CurrencyType currencyType);
}
