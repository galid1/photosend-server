package com.photosend.photosendserver01.domains.admin.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.FoundProductInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductInformationService {
    private final ProductRepository productRepository;

    @Transactional
    public void inputProductInformation(List<FoundProductInformation> foundProductInformationList) {
        foundProductInformationList.forEach((foundProductInformation) -> {
            productRepository.findById(foundProductInformation.getPid())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."))
                    .putProductInformation(foundProductInformation.toProductInformation());
        });
    }

}
