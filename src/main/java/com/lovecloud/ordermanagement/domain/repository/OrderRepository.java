package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
