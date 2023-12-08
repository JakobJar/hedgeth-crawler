package com.hedgeth.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class TokenQuote {

    private final String tokenAddress;
    private final long time;
    @EqualsAndHashCode.Exclude
    private final BigDecimal price;
}
