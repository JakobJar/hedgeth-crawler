package com.hedgeth.crawler.converter;

public class DummyAddressConverter implements AddressConverter {

    @Override
    public String convert(String address) {
        return address;
    }
}
