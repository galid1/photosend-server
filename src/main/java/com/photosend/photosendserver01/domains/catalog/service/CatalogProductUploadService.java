package com.photosend.photosendserver01.domains.catalog.service;

import com.photosend.photosendserver01.common.event.EmailEvent;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductImageInformation;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.ProductUploadResponse;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.UploadedProduct;
import com.photosend.photosendserver01.domains.user.domain.user.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogProductUploadService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public List<ProductUploadResponse> uploadProductImages(long userId, List<UploadedProduct> uploadedProductList) {
        // Storage에 업로드
        uploadImageToStorage(uploadedProductList);

        // Email Event emit
        publishEmailEvent(uploadedProductList
                .stream()
                .map(uploadedProduct -> uploadedProduct.getImageBytes())
                .collect(Collectors.toList())
        );

        // 영속화
        List<ProductUploadResponse> productUploadResponses = addProductEntityToRepository(uploadedProductList);
        
        addUploadedProductIdListToUser(userId, productUploadResponses);
        return productUploadResponses;
    }

    private void uploadImageToStorage(List<UploadedProduct> uploadedProductList) {
        uploadedProductList
                .forEach(uploadedProduct -> {
                    fileUtil.uploadFile(uploadedProduct.getUploadedImageFilePath()
                            , uploadedProduct.getImageBytes());
                });
    }

    private void publishEmailEvent(List<byte[]> imageByteArrayList) {
        eventPublisher.publishEvent(new EmailEvent(imageByteArrayList));
    }

    private List<ProductUploadResponse> addProductEntityToRepository(List<UploadedProduct> uploadedProductList) {
        return uploadedProductList
                .stream()
                .map(uploadedProduct -> {
                    long uploadedProductId = productRepository.save(ProductEntity
                            .builder()
                            .productImageInformation(ProductImageInformation.builder()
                                    .productImagePath(uploadedProduct.getUploadedImageFilePath())
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
