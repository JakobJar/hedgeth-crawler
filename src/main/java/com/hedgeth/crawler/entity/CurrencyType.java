package com.hedgeth.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CurrencyType {

    USD("US Dollar", "usd");

    private final String name;
    private final String coinGeckoName;
}
