package com.hedgeth.crawler.util;

public final class AddressUtil {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String getHexAddress(byte[] address) {
        char[] hexChars = new char[address.length * 2];
        for (int j = 0; j < address.length; j++) {
            int v = address[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
