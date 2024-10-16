package com.lovecloud.ordermanagement.domain;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"funding_id", "order_id"}) // 복합 유니크 제약 설정
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetails extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;

    @Builder
    public OrderDetails(Order order, Funding funding) {
        this.order = order;
        this.funding = funding;
    }
}
