package com.photosend.photosendserver01.domains.admin.service;

import com.photosend.photosendserver01.domains.user.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundProductInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminProductInformationService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void inputProductInformation(long userId, List<FoundProductInformation> foundProductInformationList) {
        foundProductInformationList.forEach((foundProductInformation) -> {
            productRepository.findByPidAndUserUserId(foundProductInformation.getPid(), userId)
                    .putProductInformation(foundProductInformation.toProductInformation());
        });
    }
}
