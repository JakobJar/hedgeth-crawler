package com.hedgeth.crawler.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ValuedTokenQuote  {

    private final TokenQuote tokenQuote;
    private final BigDecimal amount;

    public BigDecimal getValue() {
        return this.tokenQuote.getPrice().multiply(this.amount);
    }
}
