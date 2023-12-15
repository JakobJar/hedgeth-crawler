package com.hedgeth.crawler.converter;

import com.hedgeth.crawler.entity.ValuedTokenQuote;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import java.math.BigDecimal;
import java.util.Collection;

public class InfluxEntityConverterImpl implements InfluxEntityConverter {

    @Override
    public Point toFundValuePoint(long time, String fundAddress, Collection<ValuedTokenQuote> tokenOwnership) {
        var valueSum = tokenOwnership.stream()
                .map(ValuedTokenQuote::getValue)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        return Point.measurement("fund_value")
                .addTag("fund_address", fundAddress)
                .addField("value", valueSum)
                .time(time, WritePrecision.MS);
    }

    @Override
    public Point toFundTokenValuePoint(long time, String fundAddress, ValuedTokenQuote tokenOwnership) {
        return Point.measurement("fund_token_value")
                .addTag("fund_address", fundAddress)
                .addTag("token_address", tokenOwnership.getTokenQuote().getTokenAddress())
                .addField("price", tokenOwnership.getTokenQuote().getPrice())
                .addField("amount", tokenOwnership.getAmount())
                .time(time, WritePrecision.MS);
    }
}
