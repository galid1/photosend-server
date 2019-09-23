package com.photosend.photosendserver01.domains.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByPidAndUserUserId(Long pid, Long userId);

    ProductEntity findByPidAndUserUserIdAndProductStateNot(Long productId, Long userId, ProductState productState);

    List<ProductEntity> findByUserUserIdAndProductStateNot(Long userId, ProductState productState);
}
