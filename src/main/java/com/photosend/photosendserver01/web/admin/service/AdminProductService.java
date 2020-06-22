package com.photosend.photosendserver01.web.admin.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public Map<User, List<ProductEntity>> getUploadedProductListGroupByUploaderId() {
//        List<ProductEntity> byProductState = productRepository.findByProductState(ProductState.UPLOADED);
        List<ProductEntity> allProductList = productRepository.findAll();

        Map<Long, List<ProductEntity>> productListGroupByUploaderId = allProductList.stream()
                .collect(Collectors.groupingBy(productEntity -> productEntity.getUploaderId()));

        Map<User, List<ProductEntity>> productListGroupByUploader = productListGroupByUploaderId.entrySet().stream()
                .collect(Collectors.toMap(e -> new User(e.getKey(), userRepository.findById(e.getKey()).get().getUserInformation().getName()), Map.Entry::getValue));

        return productListGroupByUploader;
    }


    @Getter
    private class User {
        private long userId;
        private String name;

        public User(long userId, String name) {
            this.userId = userId;
            this.name = name;
        }
    }

}
