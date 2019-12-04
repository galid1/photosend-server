package com.photosend.photosendserver01.domains.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByOrdererId(Long ordererId);
}
