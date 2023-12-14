package com.hedgeth.crawler.cron;

import com.google.inject.Inject;
import com.hedgeth.crawler.converter.InfluxEntityConverter;
import com.hedgeth.crawler.datasource.APIDataSource;
import com.influxdb.client.InfluxDBClient;
import lombok.extern.slf4j.Slf4j;
import org.web3j.model.IFund;
import org.web3j.model.IFundFactory;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TokenQuotesCronjob extends TimerTask {

    private final IFundFactory fundFactory;
    private final APIDataSource apiDataSource;
    private final InfluxDBClient influxClient;
    private final InfluxEntityConverter influxEntityConverter;
    private final Web3j web3j;
    private final TransactionManager transactionManager;
    private final ContractGasProvider contractGasProvider;

    @Inject
    public TokenQuotesCronjob(IFundFactory fundFactory, APIDataSource apiDataSource,
                              InfluxDBClient influxClient, InfluxEntityConverter influxEntityConverter,
                              Web3j web3j, TransactionManager transactionManager,
                              ContractGasProvider contractGasProvider) {
        this.fundFactory = fundFactory;
        this.apiDataSource = apiDataSource;
        this.influxClient = influxClient;
        this.influxEntityConverter = influxEntityConverter;
        this.web3j = web3j;
        this.transactionManager = transactionManager;
        this.contractGasProvider = contractGasProvider;
    }

    @Override
    public void run() {
        var fundValues = this.loadOpenFundAddresses().stream()
                .map(this::getFund)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(IFund::getContractAddress, this::getFundTokenValues));
        // TODO: Store fund values in database.
        var tokenAddresses = fundValues.values().stream()
                .flatMap(Collection::stream)
                .map(assetValue -> assetValue.token)
                .map(address -> "0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48")
                .distinct()
                .toList();
        System.out.println(tokenAddresses);
        var tokenQuotes = this.apiDataSource.getCurrentQuotes(tokenAddresses);
        System.out.println(tokenQuotes);
    }

    private List<String> loadOpenFundAddresses() {
        try {
            return this.fundFactory.getOpenFunds().send();
        } catch (Exception e) {
            log.error("Failed to load open fund addresses.", e);
        }
        return Collections.emptyList();
    }

    private Optional<IFund> getFund(String address) {
        try {
            return Optional.of(IFund.load(address, this.web3j, this.transactionManager, this.contractGasProvider));
        } catch (Exception e) {
            log.warn("Failed to load fund with address {}.", address);
            return Optional.empty();
        }
    }

    private List<IFund.AssetValue> getFundTokenValues(IFund fund) {
        try {
            return fund.getAssetValues().send();
        } catch (Exception e) {
            log.warn("Failed to load token values for fund with address {}.", fund.getContractAddress());
            return Collections.emptyList();
        }
    }
}
