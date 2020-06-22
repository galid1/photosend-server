package com.photosend.photosendserver01.domains.catalog.service;

import com.photosend.photosendserver01.common.event.EmailEvent;
import com.photosend.photosendserver01.common.util.email.EmailType;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductImageInformation;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.ProductUploadResponse;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.UploadedProduct;
import com.photosend.photosendserver01.domains.user.domain.user.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogProductUploadService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final FileUtil fileUtil;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public List<ProductUploadResponse> uploadProductImages(long uploaderId, List<UploadedProduct> usersUploadProductList) {
        // Storage에 업로드
        uploadImageToStorage(usersUploadProductList);

        // Email Event emit
        eventPublisher.publishEvent(new EmailEvent(EmailType.IMAGE_UPLOADED, uploaderId, usersUploadProductList
                .stream()
                .map(uploadedProduct -> uploadedProduct.getImageBytes())
                .collect(Collectors.toList())));

        // 영속화
        List<ProductUploadResponse> productUploadResponses = addProductEntityToRepository(uploaderId, usersUploadProductList);
        
        addUploadedProductIdListToUser(uploaderId, productUploadResponses);
        return productUploadResponses;
    }

    private void uploadImageToStorage(List<UploadedProduct> usersUploadProductList) {
        usersUploadProductList
            .forEach(usersUploadProduct -> {
                fileUtil.uploadFile(usersUploadProduct.getUploadedImageFilePath()
                        , usersUploadProduct.getImageBytes());
            });
    }

    private List<ProductUploadResponse> addProductEntityToRepository(long uploaderId, List<UploadedProduct> uploadedProductList) {
        return uploadedProductList
                .stream()
                .map(uploadedProduct -> {
                    long uploadedProductId = productRepository.save(ProductEntity
                            .builder()
                            .uploaderId(uploaderId)
                            .productImageInformation(ProductImageInformation.builder()
                                    .productImagePath(fileUtil.getFileUploadPathForProductEntity(uploadedProduct.getUploadedImageFilePath()))
                                    .productLocation(uploadedProduct.getProductLocation())
                                    .build())
                            .build()
                    ).getPid();

                    return ProductUploadResponse.builder()
                            .uploadedProductId(uploadedProductId)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private void addUploadedProductIdListToUser(long userId, List<ProductUploadResponse> productUploadResponses) {
        UserEntity uploadUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        productUploadResponses
                .forEach(productUploadResponse -> {
                    uploadUser.addProduct(productUploadResponse.getUploadedProductId());
                });
    }
}
