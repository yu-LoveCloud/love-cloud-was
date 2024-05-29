package com.lovecloud.ordermanagement.presentation;

import com.lovecloud.ordermanagement.application.OrderCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderCreateService orderCreateService;

}
