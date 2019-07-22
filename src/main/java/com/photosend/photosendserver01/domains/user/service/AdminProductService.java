package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.ProductEntity;
import com.photosend.photosendserver01.domains.user.domain.ProductInformation;
import com.photosend.photosendserver01.domains.user.domain.ProductRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundProductInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void putFoundProductInformation(String uid, List<FoundProductInformation> foundProductInformationList) {
        foundProductInformationList.stream().forEach(product -> {
            ProductInformation newProductInformation = ProductInformation.builder()
                                                            .brand(product.getBrand())
                                                            .name(product.getName())
                                                            .price(product.getPrice())
                                                            .size(product.getSize())
                                                            .build();

            ProductEntity productEntity = productRepository.findByPidAndUserWechatUid(product.getPid(), uid);
            productEntity.putProductInformation(newProductInformation);
        });
    }
}
