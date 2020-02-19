package com.photosend.photosendserver01.web.admin.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminProductService {
    @Autowired
    private ProductRepository productRepository;

    public Map<Long, List<ProductEntity>> getUploadedProductListGroupByUploaderId() {
        List<ProductEntity> byProductState = productRepository.findByProductState(ProductState.UPLOADED);

        Map<Long, List<ProductEntity>> productListGroupByUploaderId = byProductState.stream()
                .collect(Collectors.groupingBy(productEntity -> productEntity.getUploaderId()));

        return productListGroupByUploaderId;
    }

}
