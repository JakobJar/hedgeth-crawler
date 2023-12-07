package com.hedgeth.crawler.entity;

import com.hedgeth.crawler.util.AddressUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Token {

    private final byte[] address;
    private final String name;
    private final String symbol;

    public String getHexAddress() {
        return AddressUtil.getHexAddress(this.address);
    }
}
