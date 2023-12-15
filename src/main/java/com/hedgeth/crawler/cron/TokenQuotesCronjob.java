package com.hedgeth.crawler.cron;

import com.google.inject.Inject;
import com.hedgeth.crawler.converter.InfluxEntityConverter;
import com.hedgeth.crawler.datasource.APIDataSource;
import com.hedgeth.crawler.entity.TokenQuote;
import com.hedgeth.crawler.entity.ValuedTokenQuote;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import lombok.extern.slf4j.Slf4j;
import org.web3j.model.IFund;
import org.web3j.model.IFundFactory;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TokenQuotesCronjob extends TimerTask {

    private final IFundFactory fundFactory;
    private final APIDataSource apiDataSource;
    private final WriteApiBlocking influxClient;
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
        this.influxClient = influxClient.getWriteApiBlocking();
        this.influxEntityConverter = influxEntityConverter;
        this.web3j = web3j;
        this.transactionManager = transactionManager;
        this.contractGasProvider = contractGasProvider;
    }

    @Override
    public void run() {
        // Loading all open funds and their token values
        Map<String, List<IFund.AssetValue>> fundAssets = this.loadOpenFundAddresses().stream()
                .map(this::getFund)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(fund -> !fund.getContractAddress().equals("0x0000000000000000000000000000000000000000"))
                .collect(Collectors.toMap(IFund::getContractAddress, this::getFundTokenAssets));

        // Loading all distinct addresses of tokens that are present in any fund
        List<String> tokenAddresses = fundAssets.values().stream()
                .flatMap(Collection::stream)
                .map(assetValue -> assetValue.token)
                .map(address -> "0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48")
                .distinct()
                .toList();
        log.debug("Fetched token addresses: {}", tokenAddresses);

        // Loading quotes for all tokens
        Map<String, TokenQuote> tokenQuotes = this.apiDataSource.getCurrentQuotes(tokenAddresses);
        log.debug("Fetched token quotes: {}", tokenQuotes);

        for (var entry : fundAssets.entrySet()) {
            var fundAddress = entry.getKey();
            var fundTokenAssets = entry.getValue();
            var time = System.currentTimeMillis();

            // Converting token values to valued token quotes
            var valuedTokenQuotes = fundTokenAssets.stream()
                    .map(tokenValue -> {
                        var tokenQuote = tokenQuotes.get("0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48");
                        var amount = new BigDecimal(tokenValue.value, tokenValue.decimals.intValue());
                        return new ValuedTokenQuote(tokenQuote, amount);
                    })
                    .toList();

            // Writing token values and fund value to InfluxDB
            for (ValuedTokenQuote valuedTokenQuote : valuedTokenQuotes) {
                var tokenValuePoint = this.influxEntityConverter.toFundTokenValuePoint(time, fundAddress, valuedTokenQuote);
                this.influxClient.writePoint(tokenValuePoint);
            }
            var fundValuePoint = this.influxEntityConverter.toFundValuePoint(time, fundAddress, valuedTokenQuotes);
            this.influxClient.writePoint(fundValuePoint);
        }

        log.info("Loaded {} funds with different {} tokens.", fundAssets.size(), tokenAddresses.size());
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

    private List<IFund.AssetValue> getFundTokenAssets(IFund fund) {
        try {
            return fund.getAssetValues().send();
        } catch (Exception e) {
            log.warn("Failed to load token values for fund with address {}.", fund.getContractAddress());
            return Collections.emptyList();
        }
    }
}
