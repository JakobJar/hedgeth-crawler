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

    default Optional<TokenQuote> getCurrentQuote(String tokenAddress) {
        return getCurrentQuote(tokenAddress, DEFAULT_CURRENCY_TYPE);
    }

    Optional<TokenQuote> getCurrentQuote(String tokenAddress, CurrencyType currencyType);
}
