package com.lovecloud.ordermanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import com.lovecloud.ordermanagement.application.command.UpdateDeliveryAddressCommand;
import com.lovecloud.usermanagement.domain.Couple;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "delivery_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddress extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_id")
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

    @Column(name = "delivery_memo", nullable = false, length = 100)
    private String deliveryMemo;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id", nullable = false)
    private Couple couple;

    @Builder
    public DeliveryAddress(String deliveryName, String receiverName, String receiverPhoneNumber,
            String zipCode, String address, String detailAddress, String deliveryMemo,
            Couple couple) {
        this.deliveryName = deliveryName;
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.zipCode = zipCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.deliveryMemo = deliveryMemo;
        this.couple = couple;
    }

    public void update(UpdateDeliveryAddressCommand command) {
        this.deliveryName = command.deliveryName();
        this.zipCode = command.zipCode();
        this.address = command.address();
        this.detailAddress = command.detailAddress();
        this.deliveryMemo = command.deliveryMemo();
        this.receiverName = command.receiverName();
        this.receiverPhoneNumber = command.receiverPhoneNumber();
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}
