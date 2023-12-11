package com.hedgeth.crawler;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Stage;
import com.hedgeth.crawler.database.DatabaseModule;
import com.hedgeth.crawler.datasource.DataSourceModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HedgethCrawler extends AbstractModule {

    public static void main(String[] args) {
        new HedgethCrawler().start();
    }

    public void start() {
        var stage = Stage.valueOf(System.getenv().getOrDefault("STAGE", "PRODUCTION"));
        var injector = Guice.createInjector(stage, this, new DataSourceModule(), new DatabaseModule());
    }
}
