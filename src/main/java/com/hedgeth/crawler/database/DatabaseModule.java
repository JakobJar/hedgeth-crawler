package com.hedgeth.crawler.database;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

public final class DatabaseModule extends AbstractModule {

    @Provides
    @Singleton
    private InfluxDBClient provideInfluxDBClient() {
        var url = System.getenv("INFLUX_URL");
        var token = System.getenv("INFLUX_TOKEN").toCharArray();
        var org = System.getenv("INFLUX_ORG");
        var bucket = System.getenv("INFLUX_BUCKET");
        return InfluxDBClientFactory.create(url, token, org, bucket);
    }
}
