package com.hedgeth.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Token {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private final byte[] address;
    private final String name;
    private final String symbol;

    public String getHexAddress() {
        char[] hexChars = new char[address.length * 2];
        for (int j = 0; j < address.length; j++) {
            int v = address[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
