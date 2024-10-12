package com.lovecloud.ordermanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.ordermanagement.application.OrderCreateService;
import com.lovecloud.ordermanagement.presentation.request.CreateOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderCreateService orderCreateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<Long> createOrder(@Valid @RequestBody CreateOrderRequest request,
                                            @AuthenticationPrincipal SecurityUser securityUser) {
        final Long orderId = orderCreateService.createOrder(request.toCommand(securityUser.user().getId()));
        return ResponseEntity.created(URI.create("/orders/" + orderId)).build();
    }

    //주문 취소
    @PatchMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public void cancelOrder(@PathVariable Long orderId, @AuthenticationPrincipal SecurityUser securityUser) {
        orderCreateService.cancelOrder(orderId, securityUser.user().getId());
    }

}
