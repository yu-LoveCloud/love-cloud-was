package com.lovecloud.ordermanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Column(name = "delivery_name", nullable = false, length = 100)
    private String deliveryName;

    @Column(name = "receiver_name", nullable = false, length = 100)
    private String receiverName;

    @Column(name = "receiver_phone_number", nullable = false, length = 100)
    private String receiverPhoneNumber;

    @Column(name = "zip_code", nullable = false, length = 100)
    private String zipCode;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "detail_address", nullable = false, length = 100)
    private String detailAddress;

    @Column(name = "delivery_memo", length = 100)
    private String deliveryMemo;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false, length = 100)
    private DeliveryStatus deliveryStatus = DeliveryStatus.PENDING;

    @Builder
    public Delivery(String deliveryName, String receiverName, String receiverPhoneNumber,
            String zipCode, String address, String detailAddress, String deliveryMemo) {
        this.deliveryName = deliveryName;
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.zipCode = zipCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.deliveryMemo = deliveryMemo;
    }
}
