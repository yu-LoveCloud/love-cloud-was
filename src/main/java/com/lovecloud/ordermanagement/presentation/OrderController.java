package com.lovecloud.ordermanagement.presentation;

import com.lovecloud.ordermanagement.application.OrderCreateService;
import com.lovecloud.ordermanagement.presentation.request.OrderCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderCreateService orderCreateService;

    @PostMapping
    public ResponseEntity<Long> createOrder(@Valid @RequestBody OrderCreateRequest request,
                                            Long userId) {
        final Long orderId = orderCreateService.createOrder(request.toCommand(userId));
        return ResponseEntity.created(URI.create("/orders/" + orderId)).build();
    }
}
