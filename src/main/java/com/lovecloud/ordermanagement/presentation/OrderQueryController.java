package com.lovecloud.ordermanagement.presentation;

import com.lovecloud.ordermanagement.application.OrderQueryService;
import com.lovecloud.ordermanagement.query.response.OrderDetailResponse;
import com.lovecloud.ordermanagement.query.response.OrderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderQueryController {
    private final OrderQueryService orderQueryService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> detailOrder(@PathVariable Long orderId, Long userId) {
        final OrderDetailResponse order = orderQueryService.findById(orderId, userId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderListResponse>> listOrders(Long userId) {
        final List<OrderListResponse> orders = orderQueryService.findAllByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}
