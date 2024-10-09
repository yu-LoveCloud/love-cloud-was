package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.exception.OrderNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    default Order findByIdOrThrow(Long orderId){
        return findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    @Query("select distinct o from Order o " +
            "join fetch o.couple c " +
            "join fetch o.delivery d " +
            "join fetch o.orderDetails od " +
            "join fetch od.funding f " +
            "join fetch f.productOptions po " +
            "join fetch po.product p " +
            "where o.id = :id")
    Optional<Order> findById(Long id);

//    @Query("select o from Order o where o.couple.bride.id = :userId or o.couple.groom.id = :userId")
    @Query("SELECT distinct o FROM Order o " +
            "JOIN FETCH o.couple c " +
            "JOIN FETCH o.delivery d " +
            "JOIN FETCH o.orderDetails od " +
            "JOIN FETCH od.funding f " +
            "JOIN FETCH f.productOptions po " +
            "JOIN FETCH po.product p " +
            "WHERE c.id IN (SELECT c2.id FROM Couple c2 WHERE c2.bride.id = :userId OR c2.groom.id = :userId)")
    List<Order> findAllByUserId(Long userId);
}
