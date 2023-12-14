package com.hedgeth.crawler.converter;

import com.google.inject.AbstractModule;

public class ConverterModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(InfluxEntityConverter.class).to(InfluxEntityConverterImpl.class);
    }
}
