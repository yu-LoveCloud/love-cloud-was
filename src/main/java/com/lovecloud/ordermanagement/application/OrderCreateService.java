package com.lovecloud.ordermanagement.application;

import com.lovecloud.ordermanagement.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCreateService {
    private final OrderRepository orderRepository;
}
