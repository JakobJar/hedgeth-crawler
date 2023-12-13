package com.hedgeth.crawler.cron;

import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.web3j.abi.datatypes.Address;
import org.web3j.model.IFund;
import org.web3j.model.IFundFactory;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.util.List;
import java.util.Optional;

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
        var fundAddresses = this.loadOpenFundAddresses().stream()
                .map(address -> this.getFund(address.getValue()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                ;

    }

    private List<Address> loadOpenFundAddresses() {
        try {
            return this.fundFactory.getOpenFunds().send();
        } catch (Exception e) {
            throw new RuntimeException("Error while loading open funds.", e);
        }
    }

    private Optional<IFund> getFund(String address) {
        try {
            return Optional.of(IFund.load(address, this.web3j, this.transactionManager, this.contractGasProvider));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
