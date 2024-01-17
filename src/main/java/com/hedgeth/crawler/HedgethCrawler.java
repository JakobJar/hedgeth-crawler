package com.hedgeth.crawler;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Stage;
import com.hedgeth.crawler.converter.ConverterModule;
import com.hedgeth.crawler.cron.TokenQuotesCronjob;
import com.hedgeth.crawler.database.DatabaseModule;
import com.hedgeth.crawler.datasource.DataSourceModule;
import com.hedgeth.crawler.ethereum.EthereumModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HedgethCrawler extends AbstractModule {

    public static void main(String[] args) {
        new HedgethCrawler().start();
    }

    public void start() {
        var stage = Stage.valueOf(System.getenv().getOrDefault("STAGE", "PRODUCTION"));
        var injector = Guice.createInjector(stage, this, new DataSourceModule(),
                new DatabaseModule(), new EthereumModule(), new ConverterModule());

        var cronjobTimer = new Timer("cronjob-timer", false);
        var tokenQuotesCronjob = injector.getInstance(TokenQuotesCronjob.class);
        cronjobTimer.scheduleAtFixedRate(tokenQuotesCronjob, 0, 60 * 60 * 1000);

        log.info("Hedgeth Crawler successfully started.");
    }
}
