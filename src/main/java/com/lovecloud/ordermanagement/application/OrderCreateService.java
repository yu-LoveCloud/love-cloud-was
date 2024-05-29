package com.lovecloud.ordermanagement.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCreateService {
    private final OrderRepository orderRepository;
}
