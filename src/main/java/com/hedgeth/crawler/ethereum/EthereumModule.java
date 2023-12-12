package com.hedgeth.crawler.ethereum;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.web3j.model.IFund;
import org.web3j.model.IFundFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

public class EthereumModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    private IFundFactory provideFundFactoryContract(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        var address = System.getenv("FUND_FACTORY_ADDRESS");
        return IFundFactory.load(address, web3j, transactionManager, contractGasProvider);
    }

    @Provides
    @Singleton
    private Web3j provideWeb3j() {
        var url = System.getenv("JSON_RPC_URL");
        return Web3j.build(new HttpService(url));
    }

    @Provides
    @Singleton
    private ContractGasProvider provideContractGasProvider() {
        return new DefaultGasProvider();
    }

    @Provides
    @Singleton
    private TransactionManager provideTransactionManager(Web3j web3j) {
        var address = System.getenv("FROM_ADDRESS");
        return new ReadonlyTransactionManager(web3j, address);
    }
}
