package com.photosend.photosendserver01.domains.catalog.domain.product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByProductState(ProductState productState);

    List<ProductEntity> findByProductStateOrderByCreatedDateDesc(ProductState productState, Pageable pageable);

    ProductEntity findFirst1ByProductStateOrderByCreatedDateDesc(ProductState productState);

    List<ProductEntity> findByUploaderId(long uploaderId);

    List<ProductEntity> findByUploaderIdOrderByProductState(long uploaderId);
}
