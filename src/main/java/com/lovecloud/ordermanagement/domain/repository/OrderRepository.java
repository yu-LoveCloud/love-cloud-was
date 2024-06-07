package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.exception.OrderNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    default Order findByIdOrThrow(Long orderId){
        return findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Query("select o from Order o where o.couple.bride.id = :userId or o.couple.groom.id = :userId")
    List<Order> findAllByUserId(Long userId);
}
