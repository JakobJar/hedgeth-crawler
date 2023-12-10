package com.hedgeth.crawler.converter;

import com.hedgeth.crawler.entity.ValuedTokenQuote;
import com.influxdb.client.write.Point;

import java.util.Collection;
import java.util.List;

public interface InfluxEntityConverter {

    Point toFundValuePoint(long time, String fundAddress, Collection<ValuedTokenQuote> tokenOwnership);

    Point toFundTokenValuePoint(long time, String fundAddress, ValuedTokenQuote tokenOwnership);

    default List<Point> toFundTokenValuePoints(long time, String fundAddress, Collection<ValuedTokenQuote> tokenOwnership) {
        return tokenOwnership.stream()
                .map(tokenQuote -> toFundTokenValuePoint(time, fundAddress, tokenQuote))
                .toList();
    }
}
