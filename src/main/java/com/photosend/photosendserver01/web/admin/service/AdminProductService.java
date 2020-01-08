package com.photosend.photosendserver01.web.admin.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getUploadedProductList() {
        return productRepository.findByProductState(ProductState.UPLOADED);
    }
}
