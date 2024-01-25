package org.web3j.model;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
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
public class IFund extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_SWAPASSETS = "swapAssets";

    public static final String FUNC_INVEST = "invest";

    public static final String FUNC_PAYOUT = "payout";

    public static final String FUNC_CLOSE = "close";

    public static final String FUNC_WITHDRAWFEES = "withdrawFees";

    public static final String FUNC_MANAGER = "manager";

    public static final String FUNC_MINIMUMINVESTMENT = "minimumInvestment";

    public static final String FUNC_INVESTMENTFEE = "investmentFee";

    public static final String FUNC_PERFORMANCEFEE = "performanceFee";

    public static final String FUNC_FUNDRAISINGCLOSE = "fundRaisingClose";

    public static final String FUNC_FUNDCLOSE = "fundClose";

    public static final String FUNC_GETINVESTMENTS = "getInvestments";

    public static final String FUNC_GETASSETVALUES = "getAssetValues";

    public static final Event ASSETSWAP_EVENT = new Event("AssetSwap", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event INVESTMENT_EVENT = new Event("Investment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected IFund(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IFund(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IFund(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IFund(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<AssetSwapEventResponse> getAssetSwapEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ASSETSWAP_EVENT, transactionReceipt);
        ArrayList<AssetSwapEventResponse> responses = new ArrayList<AssetSwapEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AssetSwapEventResponse typedResponse = new AssetSwapEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenIn = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.tokenOut = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amountIn = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amountOut = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AssetSwapEventResponse getAssetSwapEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ASSETSWAP_EVENT, log);
        AssetSwapEventResponse typedResponse = new AssetSwapEventResponse();
        typedResponse.log = log;
        typedResponse.tokenIn = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.tokenOut = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amountIn = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amountOut = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<AssetSwapEventResponse> assetSwapEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAssetSwapEventFromLog(log));
    }

    public Flowable<AssetSwapEventResponse> assetSwapEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ASSETSWAP_EVENT));
        return assetSwapEventFlowable(filter);
    }

    public static List<InvestmentEventResponse> getInvestmentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INVESTMENT_EVENT, transactionReceipt);
        ArrayList<InvestmentEventResponse> responses = new ArrayList<InvestmentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InvestmentEventResponse typedResponse = new InvestmentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.investor = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static InvestmentEventResponse getInvestmentEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INVESTMENT_EVENT, log);
        InvestmentEventResponse typedResponse = new InvestmentEventResponse();
        typedResponse.log = log;
        typedResponse.investor = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<InvestmentEventResponse> investmentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getInvestmentEventFromLog(log));
    }

    public Flowable<InvestmentEventResponse> investmentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INVESTMENT_EVENT));
        return investmentEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> swapAssets(byte[] path, BigInteger amountIn, BigInteger amountOutMinimum, String tokenIn, String tokenOut) {
        final Function function = new Function(
                FUNC_SWAPASSETS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(path), 
                new org.web3j.abi.datatypes.generated.Uint256(amountIn), 
                new org.web3j.abi.datatypes.generated.Uint256(amountOutMinimum), 
                new org.web3j.abi.datatypes.Address(160, tokenIn), 
                new org.web3j.abi.datatypes.Address(160, tokenOut)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> invest(BigInteger amount) {
        final Function function = new Function(
                FUNC_INVEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> payout() {
        final Function function = new Function(
                FUNC_PAYOUT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> close() {
        final Function function = new Function(
                FUNC_CLOSE, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<String> manager() {
        final Function function = new Function(FUNC_MANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> minimumInvestment() {
        final Function function = new Function(FUNC_MINIMUMINVESTMENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<BigInteger> fundRaisingClose() {
        final Function function = new Function(FUNC_FUNDRAISINGCLOSE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> fundClose() {
        final Function function = new Function(FUNC_FUNDCLOSE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getInvestments() {
        final Function function = new Function(FUNC_GETINVESTMENTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<InvestmentValue>>() {}));
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

    public RemoteFunctionCall<List> getAssetValues() {
        final Function function = new Function(FUNC_GETASSETVALUES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<AssetValue>>() {}));
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
    public static IFund load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IFund(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IFund load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IFund(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IFund load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IFund(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IFund load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IFund(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class InvestmentValue extends StaticStruct {
        public String investor;

        public BigInteger value;

        public InvestmentValue(String investor, BigInteger value) {
            super(new org.web3j.abi.datatypes.Address(160, investor), 
                    new org.web3j.abi.datatypes.generated.Uint256(value));
            this.investor = investor;
            this.value = value;
        }

        public InvestmentValue(Address investor, Uint256 value) {
            super(investor, value);
            this.investor = investor.getValue();
            this.value = value.getValue();
        }
    }

    public static class AssetValue extends DynamicStruct {
        public String token;

        public String name;

        public String symbol;

        public BigInteger decimals;

        public BigInteger value;

        public AssetValue(String token, String name, String symbol, BigInteger decimals, BigInteger value) {
            super(new org.web3j.abi.datatypes.Address(160, token), 
                    new org.web3j.abi.datatypes.Utf8String(name), 
                    new org.web3j.abi.datatypes.Utf8String(symbol), 
                    new org.web3j.abi.datatypes.generated.Uint8(decimals), 
                    new org.web3j.abi.datatypes.generated.Uint256(value));
            this.token = token;
            this.name = name;
            this.symbol = symbol;
            this.decimals = decimals;
            this.value = value;
        }

        public AssetValue(Address token, Utf8String name, Utf8String symbol, Uint8 decimals, Uint256 value) {
            super(token, name, symbol, decimals, value);
            this.token = token.getValue();
            this.name = name.getValue();
            this.symbol = symbol.getValue();
            this.decimals = decimals.getValue();
            this.value = value.getValue();
        }
    }

    public static class AssetSwapEventResponse extends BaseEventResponse {
        public String tokenIn;

        public String tokenOut;

        public BigInteger amountIn;

        public BigInteger amountOut;
    }

    public static class InvestmentEventResponse extends BaseEventResponse {
        public String investor;

        public BigInteger amount;
    }
}
