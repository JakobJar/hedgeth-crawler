package com.hedgeth.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class TokenQuote {

    private final long time;
    private final BigDecimal price;
}
