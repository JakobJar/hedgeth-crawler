package com.hedgeth.crawler.converter;

import java.util.Optional;

public interface AddressConverter {

    Optional<String> convert(String address);
}
