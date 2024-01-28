package com.hedgeth.crawler.converter;

import java.util.Optional;

public class DummyAddressConverter implements AddressConverter {

    @Override
    public Optional<String> convert(String address) {
        return Optional.of(address);
    }
}
