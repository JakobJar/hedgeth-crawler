package com.hedgeth.crawler.converter;

import com.google.inject.Singleton;

import java.util.Optional;

@Singleton
public class DummyAddressConverter implements AddressConverter {

    @Override
    public Optional<String> convert(String address) {
        return Optional.of(address);
    }
}
