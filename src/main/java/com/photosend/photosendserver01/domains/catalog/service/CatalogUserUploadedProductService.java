package com.photosend.photosendserver01.domains.catalog.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.UserUploadedProduct;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogUserUploadedProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<UserUploadedProduct> getUsersUploadProductList(long uploaderId) {
        return productRepository.findByUploaderId(uploaderId)
                .stream()
                .map(usersUploadedProductEntity -> toUserUploadedProduct(uploaderId, usersUploadedProductEntity))
                .collect(Collectors.toList());
    }

    private UserUploadedProduct toUserUploadedProduct(long uploaderId, ProductEntity usersUploadedProductEntity) {
        return UserUploadedProduct.builder()
                    .productId(usersUploadedProductEntity.getPid())
                    .productImagePath(usersUploadedProductEntity.getProductImageInformation().getProductImagePath())
                    .productState(usersUploadedProductEntity.getProductState())
                    .uploadedTime(usersUploadedProductEntity.getCreatedDate())
                    .uploaderName(userRepository.findById(uploaderId).get().getUserInformation().getName())
                    .build();

    }
}
