package com.photosend.photosendserver01.web.admin.service;

import com.photosend.photosendserver01.domains.user.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.user.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.user.domain.product.ProductState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getUploadedProductList(long userId) {
        return productRepository.findByUserUserIdAndProductState(userId, ProductState.UPLOADED);
    }

}
