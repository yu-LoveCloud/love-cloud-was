package com.lovecloud.ordermanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import com.lovecloud.usermanagement.domain.Couple;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "orderer_name", nullable = false, length = 100)
    private String ordererName;

    @Column(name = "orderer_phone_number", nullable = false, length = 100)
    private String ordererPhoneNumber;

    @Column(name = "orderer_memo", nullable = false, length = 100)
    private String ordererMemo;

    @Column(name = "order_number", nullable = false, length = 100)
    private String orderNumber;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus = OrderStatus.ORDER_PLACED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id", nullable = false)
    private Couple couple;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    @Builder
    public Order(String ordererName, String ordererPhoneNumber, String ordererMemo, String orderNumber, LocalDateTime orderDateTime, Couple couple, Delivery delivery) {
        this.ordererName = ordererName;
        this.ordererPhoneNumber = ordererPhoneNumber;
        this.ordererMemo = ordererMemo;
        this.orderNumber = orderNumber;
        this.orderDateTime = orderDateTime;
        this.couple = couple;
        this.delivery = delivery;
    }
}
