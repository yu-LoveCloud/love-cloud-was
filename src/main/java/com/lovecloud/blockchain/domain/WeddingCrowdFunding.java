package com.lovecloud.blockchain.domain;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
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
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class WeddingCrowdFunding extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b50604051611b60380380611b6083398181016040528101906100319190610158565b60015f819055508160015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508060025f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050610196565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6100ec826100c3565b9050919050565b5f6100fd826100e2565b9050919050565b61010d816100f3565b8114610117575f80fd5b50565b5f8151905061012881610104565b92915050565b610137816100e2565b8114610141575f80fd5b50565b5f815190506101528161012e565b92915050565b5f806040838503121561016e5761016d6100bf565b5b5f61017b8582860161011a565b925050602061018c85828601610144565b9150509250929050565b6119bd806101a35f395ff3fe608060405234801561000f575f80fd5b5060043610610091575f3560e01c8063b57326d311610064578063b57326d314610109578063b6adaaff14610139578063b8035d7114610155578063dc6fa5681461018a578063f257df36146101a657610091565b80631ec32d1514610095578063368e571b146100b357806355a373d6146100cf5780638c590917146100ed575b5f80fd5b61009d6101c4565b6040516100aa9190610fbf565b60405180910390f35b6100cd60048036038101906100c8919061100f565b6101e9565b005b6100d7610432565b6040516100e49190611095565b60405180910390f35b610107600480360381019061010291906110ae565b610457565b005b610123600480360381019061011e91906110ae565b6108c6565b60405161013091906110fb565b60405180910390f35b610153600480360381019061014e919061100f565b6109d8565b005b61016f600480360381019061016a919061100f565b610c32565b6040516101819695949392919061112e565b60405180910390f35b6101a4600480360381019061019f919061100f565b610ca2565b005b6101ae610f24565b6040516101bb91906110fb565b60405180910390f35b60025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6101f1610f2a565b5f60035f8381526020019081526020015f2090508060040160019054906101000a900460ff1615610257576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161024e9061120d565b60405180910390fd5b5f816005015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205490505f81116102dc576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102d390611275565b60405180910390fd5b5f826005015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f208190555080826003015f82825461033391906112c0565b9250508190555060015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb33836040518363ffffffff1660e01b81526004016103969291906112f3565b6020604051808303815f875af11580156103b2573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906103d69190611344565b503373ffffffffffffffffffffffffffffffffffffffff167fbb28353e4598c3b9199101a66e0989549b659a59a54d2c27fbb183f1932c8e6d8260405161041d91906110fb565b60405180910390a2505061042f610f77565b50565b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b61045f610f2a565b5f60035f8481526020019081526020015f209050806004015f9054906101000a900460ff166104c3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104ba906113b9565b60405180910390fd5b8060040160019054906101000a900460ff1615610515576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161050c9061120d565b60405180910390fd5b8060020154421061055b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161055290611421565b60405180910390fd5b5f816005015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2054146105dc576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105d390611489565b60405180910390fd5b8160015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663dd62ed3e33306040518363ffffffff1660e01b81526004016106399291906114a7565b602060405180830381865afa158015610654573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061067891906114e2565b10156106b9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106b090611557565b60405180910390fd5b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166340c10f1933846040518363ffffffff1660e01b81526004016107159291906112f3565b5f604051808303815f87803b15801561072c575f80fd5b505af115801561073e573d5f803e3d5ffd5b5050505060015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3330856040518463ffffffff1660e01b81526004016107a093929190611575565b6020604051808303815f875af11580156107bc573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906107e09190611344565b5081816005015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f208190555081816003015f82825461083891906115aa565b92505081905550806001015481600301541061086a575f816004015f6101000a81548160ff0219169083151502179055505b3373ffffffffffffffffffffffffffffffffffffffff16837f0a4a91237423e0a1766a761c7cb029311d8b95d6b1b81db1b949a70c98b4e08e846040516108b191906110fb565b60405180910390a3506108c2610f77565b5050565b5f60045f8154809291906108d9906115dd565b91905055505f60035f60045481526020019081526020015f20905033815f015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550838160010181905550824261094a91906115aa565b81600201819055506001816004015f6101000a81548160ff0219169083151502179055505f8160040160016101000a81548160ff0219169083151502179055506004547f78b089ed0ff2f97dbc6d46e8ff7c2e1d24268225a64f551606c0cec14294a2d9338684600201546040516109c493929190611624565b60405180910390a260045491505092915050565b6109e0610f2a565b5f60035f8381526020019081526020015f209050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610a84576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a7b906116c9565b60405180910390fd5b8060040160019054906101000a900460ff1615610ad6576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610acd9061120d565b60405180910390fd5b806004015f9054906101000a900460ff1615610b27576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b1e90611757565b60405180910390fd5b5f8160030154905060015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb60025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16836040518363ffffffff1660e01b8152600401610bac9291906112f3565b6020604051808303815f875af1158015610bc8573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610bec9190611344565b50827fd467c1bc28c93578a2e2e99c775c5c20d8341e9a0e7d8e1b676aea9dfef8569282604051610c1d91906110fb565b60405180910390a25050610c2f610f77565b50565b6003602052805f5260405f205f91509050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001015490806002015490806003015490806004015f9054906101000a900460ff16908060040160019054906101000a900460ff16905086565b610caa610f2a565b5f60035f8381526020019081526020015f209050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610d4e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d45906117e5565b60405180910390fd5b8060040160019054906101000a900460ff1615610da0576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d9790611873565b60405180910390fd5b806004015f9054906101000a900460ff16610df0576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610de790611901565b60405180910390fd5b5f816004015f6101000a81548160ff02191690831515021790555060018160040160016101000a81548160ff02191690831515021790555060015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb825f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1683600301546040518363ffffffff1660e01b8152600401610eaa9291906112f3565b6020604051808303815f875af1158015610ec6573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610eea9190611344565b50817f97aef5197151230249a009de66e5bac6a087b4e727083bdc150726277cbd988e60405160405180910390a250610f21610f77565b50565b60045481565b60025f5403610f6e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f6590611969565b60405180910390fd5b60025f81905550565b60015f81905550565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f610fa982610f80565b9050919050565b610fb981610f9f565b82525050565b5f602082019050610fd25f830184610fb0565b92915050565b5f80fd5b5f819050919050565b610fee81610fdc565b8114610ff8575f80fd5b50565b5f8135905061100981610fe5565b92915050565b5f6020828403121561102457611023610fd8565b5b5f61103184828501610ffb565b91505092915050565b5f819050919050565b5f61105d61105861105384610f80565b61103a565b610f80565b9050919050565b5f61106e82611043565b9050919050565b5f61107f82611064565b9050919050565b61108f81611075565b82525050565b5f6020820190506110a85f830184611086565b92915050565b5f80604083850312156110c4576110c3610fd8565b5b5f6110d185828601610ffb565b92505060206110e285828601610ffb565b9150509250929050565b6110f581610fdc565b82525050565b5f60208201905061110e5f8301846110ec565b92915050565b5f8115159050919050565b61112881611114565b82525050565b5f60c0820190506111415f830189610fb0565b61114e60208301886110ec565b61115b60408301876110ec565b61116860608301866110ec565b611175608083018561111f565b61118260a083018461111f565b979650505050505050565b5f82825260208201905092915050565b7f5468652063726f776466756e64696e6720686173206265656e2063616e63656c5f8201527f65642e0000000000000000000000000000000000000000000000000000000000602082015250565b5f6111f760238361118d565b91506112028261119d565b604082019050919050565b5f6020820190508181035f830152611224816111eb565b9050919050565b7f4e6f20726566756e6461626c6520616d6f756e742e00000000000000000000005f82015250565b5f61125f60158361118d565b915061126a8261122b565b602082019050919050565b5f6020820190508181035f83015261128c81611253565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f6112ca82610fdc565b91506112d583610fdc565b92508282039050818111156112ed576112ec611293565b5b92915050565b5f6040820190506113065f830185610fb0565b61131360208301846110ec565b9392505050565b61132381611114565b811461132d575f80fd5b50565b5f8151905061133e8161131a565b92915050565b5f6020828403121561135957611358610fd8565b5b5f61136684828501611330565b91505092915050565b7f5468652063726f776466756e64696e67206973206e6f74206163746976652e005f82015250565b5f6113a3601f8361118d565b91506113ae8261136f565b602082019050919050565b5f6020820190508181035f8301526113d081611397565b9050919050565b7f5468652063726f776466756e64696e672068617320656e6465642e00000000005f82015250565b5f61140b601b8361118d565b9150611416826113d7565b602082019050919050565b5f6020820190508181035f830152611438816113ff565b9050919050565b7f596f75206861766520616c726561647920636f6e74726962757465642e0000005f82015250565b5f611473601d8361118d565b915061147e8261143f565b602082019050919050565b5f6020820190508181035f8301526114a081611467565b9050919050565b5f6040820190506114ba5f830185610fb0565b6114c76020830184610fb0565b9392505050565b5f815190506114dc81610fe5565b92915050565b5f602082840312156114f7576114f6610fd8565b5b5f611504848285016114ce565b91505092915050565b7f496e73756666696369656e7420616c6c6f77616e63652e0000000000000000005f82015250565b5f61154160178361118d565b915061154c8261150d565b602082019050919050565b5f6020820190508181035f83015261156e81611535565b9050919050565b5f6060820190506115885f830186610fb0565b6115956020830185610fb0565b6115a260408301846110ec565b949350505050565b5f6115b482610fdc565b91506115bf83610fdc565b92508282019050808211156115d7576115d6611293565b5b92915050565b5f6115e782610fdc565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff820361161957611618611293565b5b600182019050919050565b5f6060820190506116375f830186610fb0565b61164460208301856110ec565b61165160408301846110ec565b949350505050565b7f4f6e6c792074686520636f75706c652063616e20636f6d706c657465207468655f8201527f206f726465722e00000000000000000000000000000000000000000000000000602082015250565b5f6116b360278361118d565b91506116be82611659565b604082019050919050565b5f6020820190508181035f8301526116e0816116a7565b9050919050565b7f5468652063726f776466756e64696e6720686173206e6f7420656e64656420795f8201527f65742e0000000000000000000000000000000000000000000000000000000000602082015250565b5f61174160238361118d565b915061174c826116e7565b604082019050919050565b5f6020820190508181035f83015261176e81611735565b9050919050565b7f4f6e6c792074686520636f75706c652063616e2063616e63656c2074686520635f8201527f726f776466756e64696e672e0000000000000000000000000000000000000000602082015250565b5f6117cf602c8361118d565b91506117da82611775565b604082019050919050565b5f6020820190508181035f8301526117fc816117c3565b9050919050565b7f5468652063726f776466756e64696e672068617320616c7265616479206265655f8201527f6e2063616e63656c65642e000000000000000000000000000000000000000000602082015250565b5f61185d602b8361118d565b915061186882611803565b604082019050919050565b5f6020820190508181035f83015261188a81611851565b9050919050565b7f5468652063726f776466756e64696e672068617320616c726561647920656e645f8201527f6564206f72206265656e2063616e63656c65642e000000000000000000000000602082015250565b5f6118eb60348361118d565b91506118f682611891565b604082019050919050565b5f6020820190508181035f830152611918816118df565b9050919050565b7f5265656e7472616e637947756172643a207265656e7472616e742063616c6c005f82015250565b5f611953601f8361118d565b915061195e8261191f565b602082019050919050565b5f6020820190508181035f83015261198081611947565b905091905056fea2646970667358221220e7de6bc08f24a174f72edb0cc01d4c30554429962826aa3122a01b16d27e70f464736f6c634300081a0033";

    public static final String FUNC_CANCELCONTRIBUTION = "cancelContribution";

    public static final String FUNC_CANCELCROWDFUNDING = "cancelCrowdfunding";

    public static final String FUNC_COMPANYWALLET = "companyWallet";

    public static final String FUNC_COMPLETEORDER = "completeOrder";

    public static final String FUNC_CONTRIBUTE = "contribute";

    public static final String FUNC_CREATECROWDFUNDING = "createCrowdfunding";

    public static final String FUNC_CROWDFUNDINGCOUNT = "crowdfundingCount";

    public static final String FUNC_CROWDFUNDINGS = "crowdfundings";

    public static final String FUNC_TOKENCONTRACT = "tokenContract";

    public static final Event CONTRIBUTIONMADE_EVENT = new Event("ContributionMade", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CROWDFUNDINGCANCELED_EVENT = new Event("CrowdfundingCanceled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event CROWDFUNDINGCOMPLETED_EVENT = new Event("CrowdfundingCompleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CROWDFUNDINGCREATED_EVENT = new Event("CrowdfundingCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REFUND_EVENT = new Event("Refund", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected WeddingCrowdFunding(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected WeddingCrowdFunding(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected WeddingCrowdFunding(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected WeddingCrowdFunding(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ContributionMadeEventResponse> getContributionMadeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CONTRIBUTIONMADE_EVENT, transactionReceipt);
        ArrayList<ContributionMadeEventResponse> responses = new ArrayList<ContributionMadeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ContributionMadeEventResponse typedResponse = new ContributionMadeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.guest = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContributionMadeEventResponse> contributionMadeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ContributionMadeEventResponse>() {
            @Override
            public ContributionMadeEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CONTRIBUTIONMADE_EVENT, log);
                ContributionMadeEventResponse typedResponse = new ContributionMadeEventResponse();
                typedResponse.log = log;
                typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.guest = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ContributionMadeEventResponse> contributionMadeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTRIBUTIONMADE_EVENT));
        return contributionMadeEventFlowable(filter);
    }

    public List<CrowdfundingCanceledEventResponse> getCrowdfundingCanceledEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CROWDFUNDINGCANCELED_EVENT, transactionReceipt);
        ArrayList<CrowdfundingCanceledEventResponse> responses = new ArrayList<CrowdfundingCanceledEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CrowdfundingCanceledEventResponse typedResponse = new CrowdfundingCanceledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CrowdfundingCanceledEventResponse> crowdfundingCanceledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CrowdfundingCanceledEventResponse>() {
            @Override
            public CrowdfundingCanceledEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CROWDFUNDINGCANCELED_EVENT, log);
                CrowdfundingCanceledEventResponse typedResponse = new CrowdfundingCanceledEventResponse();
                typedResponse.log = log;
                typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CrowdfundingCanceledEventResponse> crowdfundingCanceledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CROWDFUNDINGCANCELED_EVENT));
        return crowdfundingCanceledEventFlowable(filter);
    }

    public List<CrowdfundingCompletedEventResponse> getCrowdfundingCompletedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CROWDFUNDINGCOMPLETED_EVENT, transactionReceipt);
        ArrayList<CrowdfundingCompletedEventResponse> responses = new ArrayList<CrowdfundingCompletedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CrowdfundingCompletedEventResponse typedResponse = new CrowdfundingCompletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.totalCollected = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CrowdfundingCompletedEventResponse> crowdfundingCompletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CrowdfundingCompletedEventResponse>() {
            @Override
            public CrowdfundingCompletedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CROWDFUNDINGCOMPLETED_EVENT, log);
                CrowdfundingCompletedEventResponse typedResponse = new CrowdfundingCompletedEventResponse();
                typedResponse.log = log;
                typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.totalCollected = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CrowdfundingCompletedEventResponse> crowdfundingCompletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CROWDFUNDINGCOMPLETED_EVENT));
        return crowdfundingCompletedEventFlowable(filter);
    }

    public List<CrowdfundingCreatedEventResponse> getCrowdfundingCreatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CROWDFUNDINGCREATED_EVENT, transactionReceipt);
        ArrayList<CrowdfundingCreatedEventResponse> responses = new ArrayList<CrowdfundingCreatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CrowdfundingCreatedEventResponse typedResponse = new CrowdfundingCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.couple = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.goal = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CrowdfundingCreatedEventResponse> crowdfundingCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CrowdfundingCreatedEventResponse>() {
            @Override
            public CrowdfundingCreatedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CROWDFUNDINGCREATED_EVENT, log);
                CrowdfundingCreatedEventResponse typedResponse = new CrowdfundingCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.couple = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.goal = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CrowdfundingCreatedEventResponse> crowdfundingCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CROWDFUNDINGCREATED_EVENT));
        return crowdfundingCreatedEventFlowable(filter);
    }

    public List<RefundEventResponse> getRefundEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REFUND_EVENT, transactionReceipt);
        ArrayList<RefundEventResponse> responses = new ArrayList<RefundEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RefundEventResponse typedResponse = new RefundEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.guest = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RefundEventResponse> refundEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RefundEventResponse>() {
            @Override
            public RefundEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REFUND_EVENT, log);
                RefundEventResponse typedResponse = new RefundEventResponse();
                typedResponse.log = log;
                typedResponse.guest = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RefundEventResponse> refundEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REFUND_EVENT));
        return refundEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelContribution(BigInteger _crowdfundingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCELCONTRIBUTION, 
                Arrays.<Type>asList(new Uint256(_crowdfundingId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelCrowdfunding(BigInteger _crowdfundingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCELCROWDFUNDING, 
                Arrays.<Type>asList(new Uint256(_crowdfundingId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> companyWallet() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COMPANYWALLET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> completeOrder(BigInteger _crowdfundingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_COMPLETEORDER, 
                Arrays.<Type>asList(new Uint256(_crowdfundingId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> contribute(BigInteger _crowdfundingId, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CONTRIBUTE, 
                Arrays.<Type>asList(new Uint256(_crowdfundingId),
                new Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createCrowdfunding(BigInteger _goal, BigInteger _duration) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATECROWDFUNDING, 
                Arrays.<Type>asList(new Uint256(_goal),
                new Uint256(_duration)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> crowdfundingCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CROWDFUNDINGCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean>> crowdfundings(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CROWDFUNDINGS, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean>>(function,
                new Callable<Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean>>() {
                    @Override
                    public Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> tokenContract() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static WeddingCrowdFunding load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new WeddingCrowdFunding(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static WeddingCrowdFunding load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new WeddingCrowdFunding(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static WeddingCrowdFunding load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new WeddingCrowdFunding(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static WeddingCrowdFunding load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new WeddingCrowdFunding(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<WeddingCrowdFunding> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _tokenContract, String _companyWallet) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _tokenContract),
                new Address(160, _companyWallet)));
        return deployRemoteCall(WeddingCrowdFunding.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<WeddingCrowdFunding> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _tokenContract, String _companyWallet) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _tokenContract),
                new Address(160, _companyWallet)));
        return deployRemoteCall(WeddingCrowdFunding.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<WeddingCrowdFunding> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _tokenContract, String _companyWallet) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _tokenContract),
                new Address(160, _companyWallet)));
        return deployRemoteCall(WeddingCrowdFunding.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<WeddingCrowdFunding> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _tokenContract, String _companyWallet) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _tokenContract),
                new Address(160, _companyWallet)));
        return deployRemoteCall(WeddingCrowdFunding.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ContributionMadeEventResponse extends BaseEventResponse {
        public BigInteger crowdfundingId;

        public String guest;

        public BigInteger amount;
    }

    public static class CrowdfundingCanceledEventResponse extends BaseEventResponse {
        public BigInteger crowdfundingId;
    }

    public static class CrowdfundingCompletedEventResponse extends BaseEventResponse {
        public BigInteger crowdfundingId;

        public BigInteger totalCollected;
    }

    public static class CrowdfundingCreatedEventResponse extends BaseEventResponse {
        public BigInteger crowdfundingId;

        public String couple;

        public BigInteger goal;

        public BigInteger endTime;
    }

    public static class RefundEventResponse extends BaseEventResponse {
        public String guest;

        public BigInteger amount;
    }
}
