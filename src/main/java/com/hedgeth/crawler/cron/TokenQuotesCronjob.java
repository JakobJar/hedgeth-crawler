package com.hedgeth.crawler.cron;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.web3j.abi.datatypes.Address;
import org.web3j.model.IFund;
import org.web3j.model.IFundFactory;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class TokenQuotesCronjob implements Runnable {

    private final IFundFactory fundFactory;
    private final Web3j web3j;
    private final TransactionManager transactionManager;
    private final ContractGasProvider contractGasProvider;

    @Inject
    public TokenQuotesCronjob(IFundFactory fundFactory, Web3j web3j,
                              TransactionManager transactionManager,
                              ContractGasProvider contractGasProvider) {
        this.fundFactory = fundFactory;
        this.web3j = web3j;
        this.transactionManager = transactionManager;
        this.contractGasProvider = contractGasProvider;
    }

    @Override
    public void run() {
        var fundValues = this.loadOpenFundAddresses().stream()
                .map(address -> this.getFund(address.getValue()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(IFund::getContractAddress, this::getFundTokenValues));
        // TODO: Store fund values in database.
        fundValues.values().stream()
                .flatMap(Collection::stream)
                .map(assetValue -> assetValue.token)
                .distinct();
    }

    private List<Address> loadOpenFundAddresses() {
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
