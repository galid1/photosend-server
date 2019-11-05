package com.photosend.photosendserver01.domains.user.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByPidAndUserUserId(Long pid, Long userId);

    ProductEntity findByPidAndUserUserIdAndProductStateNot(Long productId, Long userId, ProductState productState);

    List<ProductEntity> findByUserUserIdAndProductStateNot(Long userId, ProductState productState);

    List<ProductEntity> findByUserUserIdAndProductState(Long userId, ProductState productState);

    // for catalog
    @Query(value = "SELECT * FROM product WHERE product_state = 'populated' ORDER BY created_date DESC LIMIT ?1, 2", nativeQuery = true)
    List<ProductEntity> findRecentlyPopulatedProductListAfter(int offset);

    @Query(value = "SELECT * FROM product WHERE product_state = 'populated' ORDER BY created_date DESC LIMIT 1", nativeQuery = true)
    ProductEntity findAMostRecentProduct();
}
