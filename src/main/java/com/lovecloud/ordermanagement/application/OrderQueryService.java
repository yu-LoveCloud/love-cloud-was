package com.lovecloud.ordermanagement.application;

import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.domain.OrderDetails;
import com.lovecloud.ordermanagement.domain.repository.OrderDetailsRepository;
import com.lovecloud.ordermanagement.domain.repository.OrderRepository;
import com.lovecloud.ordermanagement.exception.UnauthorizedOrderAccessException;
import com.lovecloud.ordermanagement.query.response.OrderDetailResponse;
import com.lovecloud.productmanagement.domain.repository.MainImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderQueryService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    public OrderDetailResponse findById(Long orderId, Long userId) {
        Order order = orderRepository.findByIdOrThrow(orderId);
        List<OrderDetails> orderDetails = orderDetailsRepository.findAllByOrderId(orderId);

        //주문 작성자와 user가 같은지 확인하는 메서드
        validateOrderer(order, userId);

        //주문한 상품들의 정보, 주문 정보를 OrderDetailResponse로 변환
        return OrderDetailResponse.from(order, orderDetails);
    }

    private void validateOrderer(Order order, Long userId) {
        if(!(order.getCouple().getBride().getId().equals(userId) || order.getCouple().getGroom().getId().equals(userId))){
            throw new UnauthorizedOrderAccessException();
        }
    }
}
