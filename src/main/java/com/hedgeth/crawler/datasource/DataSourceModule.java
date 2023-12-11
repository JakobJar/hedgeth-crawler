package com.hedgeth.crawler.datasource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.net.http.HttpClient;

public final class DataSourceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(APIDataSource.class).to(CoinGeckoDataSource.class);
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
