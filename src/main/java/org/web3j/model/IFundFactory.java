package org.web3j.model;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.10.3.
 */
@SuppressWarnings("rawtypes")
public class IFundFactory extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_CREATEFUND = "createFund";

    public static final String FUNC_WITHDRAWFEES = "withdrawFees";

    public static final String FUNC_SETINVESTMENTFEE = "setInvestmentFee";

    public static final String FUNC_SETPERFORMANCEFEE = "setPerformanceFee";

    public static final String FUNC_INVESTMENTFEE = "investmentFee";

    public static final String FUNC_PERFORMANCEFEE = "performanceFee";

    public static final String FUNC_GETOPENFUNDS = "getOpenFunds";

    public static final String FUNC_GETRAISINGFUNDS = "getRaisingFunds";

    public static final String FUNC_GETFUNDS = "getFunds";

    public static final Event FUNDCREATED_EVENT = new Event("FundCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected IFundFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IFundFactory(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IFundFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IFundFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<FundCreatedEventResponse> getFundCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FUNDCREATED_EVENT, transactionReceipt);
        ArrayList<FundCreatedEventResponse> responses = new ArrayList<FundCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundCreatedEventResponse typedResponse = new FundCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.fund = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FundCreatedEventResponse getFundCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FUNDCREATED_EVENT, log);
        FundCreatedEventResponse typedResponse = new FundCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.fund = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<FundCreatedEventResponse> fundCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFundCreatedEventFromLog(log));
    }

    public Flowable<FundCreatedEventResponse> fundCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDCREATED_EVENT));
        return fundCreatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createFund(BigInteger investmentFee, BigInteger performanceFee, BigInteger fundRaisingClose, BigInteger fundClose) {
        final Function function = new Function(
                FUNC_CREATEFUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(investmentFee), 
                new org.web3j.abi.datatypes.generated.Uint16(performanceFee), 
                new org.web3j.abi.datatypes.generated.Uint256(fundRaisingClose), 
                new org.web3j.abi.datatypes.generated.Uint256(fundClose)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawFees() {
        final Function function = new Function(
                FUNC_WITHDRAWFEES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setInvestmentFee(BigInteger investmentFee) {
        final Function function = new Function(
                FUNC_SETINVESTMENTFEE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(investmentFee)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPerformanceFee(BigInteger performanceFee) {
        final Function function = new Function(
                FUNC_SETPERFORMANCEFEE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(performanceFee)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> investmentFee() {
        final Function function = new Function(FUNC_INVESTMENTFEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> performanceFee() {
        final Function function = new Function(FUNC_PERFORMANCEFEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getOpenFunds() {
        final Function function = new Function(FUNC_GETOPENFUNDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getRaisingFunds() {
        final Function function = new Function(FUNC_GETRAISINGFUNDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getFunds() {
        final Function function = new Function(FUNC_GETFUNDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    @Deprecated
    public static IFundFactory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IFundFactory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IFundFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IFundFactory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IFundFactory load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IFundFactory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IFundFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IFundFactory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class FundCreatedEventResponse extends BaseEventResponse {
        public String fund;
    }
}
