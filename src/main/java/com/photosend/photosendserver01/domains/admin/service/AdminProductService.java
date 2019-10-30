package com.photosend.photosendserver01.domains.admin.service;

import com.photosend.photosendserver01.domains.user.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.user.domain.user.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundProductInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminProductService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void inputProductInformation(long userId, List<FoundProductInformation> foundProductInformationList) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. User Id를 다시 확인하세요."));

        foundProductInformationList.forEach((foundProductInformation) -> {
           findUsersProductToInputProductInformation(foundProductInformation.getPid(), user.getProductList())
            .putProductInformation(foundProductInformation.toProductInformation());
        });
    }

    private ProductEntity findUsersProductToInputProductInformation(long productId, List<ProductEntity> productList) {
        ProductEntity filteredProductEntity = null;

        for(ProductEntity productEntity : productList) {
            if (productEntity.getPid() == productId)
                filteredProductEntity = productEntity;
        }

        if (filteredProductEntity == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다. Product ID를 확인하세요.");
        }

        return filteredProductEntity;
    }
}
