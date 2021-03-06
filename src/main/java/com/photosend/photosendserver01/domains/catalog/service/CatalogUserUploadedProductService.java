package com.photosend.photosendserver01.domains.catalog.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.FoundProductInformation;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.UserUploadedProduct;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogUserUploadedProductService {
    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public List<UserUploadedProduct> getUsersUploadProductList(long uploaderId) {
        return productRepository.findByUploaderIdOrderByProductState(uploaderId)
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
                    .foundProductInformation(FoundProductInformation.builder()
                            .name(usersUploadedProductEntity.getProductInformation().getName())
                            .brand(usersUploadedProductEntity.getProductInformation().getBrand())
                            .price(usersUploadedProductEntity.getProductInformation().getPrice())
                            .build())
                    .build();

    }
}
