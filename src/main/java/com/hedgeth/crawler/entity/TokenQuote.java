package com.hedgeth.crawler.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
public class TokenQuote {

    private final String tokenAddress;
    private final long time;
    @EqualsAndHashCode.Exclude
    private final BigDecimal price;
}
