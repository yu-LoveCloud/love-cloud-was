package com.lovecloud.ordermanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.ordermanagement.application.OrderQueryService;
import com.lovecloud.ordermanagement.query.response.OrderDetailResponse;
import com.lovecloud.ordermanagement.query.response.OrderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> detailOrder(@PathVariable Long orderId,
                                                           @AuthenticationPrincipal SecurityUser securityUser) {
        final OrderDetailResponse order = orderQueryService.findById(orderId, securityUser.user().getId());
        return ResponseEntity.ok(order);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<List<OrderListResponse>> listOrders(
            @AuthenticationPrincipal SecurityUser securityUser) {
        final List<OrderListResponse> orders = orderQueryService.findAllByUserId(securityUser.user().getId());
        return ResponseEntity.ok(orders);
    }
}
