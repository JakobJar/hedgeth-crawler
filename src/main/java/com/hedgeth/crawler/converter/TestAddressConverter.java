package com.hedgeth.crawler.converter;

import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Singleton
public class TestAddressConverter implements AddressConverter {

    private static final String SEPOLIA_MAPPING_FILE = "/addresses/sepolia.txt";

    private final Map<String, String> mapping;

    public TestAddressConverter() {
        this.mapping = new HashMap<>();
        addMapping("0x6f14c02fc1f78322cfd7d707ab90f18bad3b54f5", "0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48");
        addMapping("0x1f9840a85d5aF5bf1D1762F925BDADdC4201F984", "0x1f9840a85d5aF5bf1D1762F925BDADdC4201F984");
        addMapping("0xB6434EE024892CBD8e3364048a259Ef779542475", "0xdAC17F958D2ee523a2206206994597C13D831ec7");
    }

    @Override
    public Optional<String> convert(String address) {
        if (!this.mapping.containsKey(address)) {
            log.warn("No mapping found for address {}", address);
            return Optional.empty();
        }
        return Optional.of(this.mapping.get(address));
    }

    private void addMapping(String fromAddress, String toAddress) {
        this.mapping.put(fromAddress.toLowerCase(), toAddress.toLowerCase());
    }

    private Map<String, String> loadMapping() throws IOException, URISyntaxException {
        var mapping = new HashMap<String, String>();
        var path = Paths.get(this.getClass().getResource(SEPOLIA_MAPPING_FILE).toURI());
        Files.readAllLines(path).forEach(line -> {
            var tokens = line.split(" ");
            mapping.put(tokens[0].toLowerCase(), tokens[1].toLowerCase());
        });
        return mapping;
    }
}
