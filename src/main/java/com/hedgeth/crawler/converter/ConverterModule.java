package com.hedgeth.crawler.converter;

import com.google.inject.AbstractModule;
import com.google.inject.Stage;

public final class ConverterModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AddressConverter.class).to(binder().currentStage() == Stage.PRODUCTION ?
                DummyAddressConverter.class : TestAddressConverter.class);
        bind(InfluxEntityConverter.class).to(InfluxEntityConverterImpl.class);
    }
}
