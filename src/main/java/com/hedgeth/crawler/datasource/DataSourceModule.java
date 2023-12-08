package com.hedgeth.crawler.datasource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.hedgeth.crawler.qualifier.CoinGecko;

import java.net.http.HttpClient;

public final class DataSourceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(APIDataSource.class)
                .annotatedWith(CoinGecko.class)
                .to(CoinGeckoDataSource.class);
        bindEnv("COINGECKO_API_KEY");
        bindEnv("COINGECKO_NETWORK");
    }

    private void bindEnv(String name) {
        var value = System.getenv(name);
        if (value == null)
            value = "";
        bind(String.class).annotatedWith(Names.named(name)).toInstance(value);
    }

    @Provides
    private HttpClient provideHttpClient() {
        return HttpClient.newHttpClient();
    }

    @Provides
    @Singleton
    private Gson provideGson() {
        return new GsonBuilder().create();
    }
}
