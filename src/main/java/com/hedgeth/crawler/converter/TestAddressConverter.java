package com.hedgeth.crawler.converter;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class TestAddressConverter implements AddressConverter {

    private static final String SEPOLIA_MAPPING_FILE = "/addresses/sepolia.txt";

    private final Map<String, String> mapping;

    public TestAddressConverter() {
        Map<String, String> mapping = new HashMap<>();
        try {
            mapping = loadMapping();
        } catch (IOException | URISyntaxException e) {
            log.error("Failed to load Sepolia address mapping", e);
        }
        this.mapping = mapping;
    }

    @Override
    public Optional<String> convert(String address) {
        if (!this.mapping.containsKey(address)) {
            log.warn("No mapping found for address {}", address);
            return Optional.empty();
        }
        return Optional.of(this.mapping.get(address));
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
