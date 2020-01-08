package com.photosend.photosendserver01.domains.admin.service;

import com.photosend.photosendserver01.domains.catalog.domain.category.CategoryRepository;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.FoundProductInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class AdminProductInformationService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void inputProductInformation(List<FoundProductInformation> foundProductInformationList) {
        foundProductInformationList.forEach((foundProductInformation) -> {
//            verifyExistCategories(foundProductInformation.getCategoryIdList());
            productRepository.findById(foundProductInformation.getPid())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."))
                    .putProductInformation(foundProductInformation.toProductInformation());
        });
    }

    private void verifyExistCategories(Set<Long> categoryIdList) {
        categoryIdList
                .stream()
                .forEach(categoryId -> {
                    if(! categoryRepository.findById(categoryId).isPresent())
                        throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
                });
    }
}
