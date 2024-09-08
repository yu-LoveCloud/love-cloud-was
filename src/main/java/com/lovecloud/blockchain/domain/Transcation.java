package com.lovecloud.blockchain.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transcation extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "transaction_hash", nullable = false)
    private String transactionHash;

    @Column(name = "transaction_index", nullable = false)
    private String transactionIndex;

    @Column(name = "block_hash", nullable = false)
    private String blockHash;

    @Column(name = "block_number", nullable = false)
    private String blockNumber;

    @Column(name = "comulative_gas_used", nullable = false)
    private String cumulativeGasUsed;

    @Column(name = "gas_used", nullable = false)
    private String gasUsed;

    @Column(name = "contract_address", nullable = false)
    private String contractAddress;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "trx_from", nullable = false)
    private String trxFrom;

    @Column(name = "trx_to", nullable = false)
    private String trxTo;

    @Column(name = "value", nullable = false)
    private Integer value;
}
