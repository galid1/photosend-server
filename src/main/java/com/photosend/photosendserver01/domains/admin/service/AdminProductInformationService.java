package com.photosend.photosendserver01.domains.admin.service;

import com.photosend.photosendserver01.domains.order.domain.exception.OrderNotFoundException;
import com.photosend.photosendserver01.domains.user.domain.ProductEntity;
import com.photosend.photosendserver01.domains.user.domain.ProductInformation;
import com.photosend.photosendserver01.domains.user.domain.ProductRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundProductInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminProductInformationService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public List<Long> putFoundProductInformation(String uid, List<FoundProductInformation> foundProductInformationList) {
        List<Long> productIdList = new ArrayList<>();

        foundProductInformationList.stream().forEach(product -> {
            ProductInformation newProductInformation = ProductInformation.builder()
                                                            .brand(product.getBrand())
                                                            .name(product.getName())
                                                            .price(product.getPrice())
                                                            .size(product.getSize())
                                                            .build();

            ProductEntity productEntity = Optional.of(productRepository.findByPidAndUserWechatUid(product.getPid(), uid))
                    .orElseThrow(() -> new OrderNotFoundException("상품이 존재하지 않습니다."));
            productIdList.add(productEntity.putProductInformation(newProductInformation));
        });

        return productIdList;
    }
}
