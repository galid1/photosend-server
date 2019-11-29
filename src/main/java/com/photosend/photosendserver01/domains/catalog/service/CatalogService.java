package com.photosend.photosendserver01.domains.catalog.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductInformation;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductState;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.*;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private static long ADMIN_ID = 1l;

    public CheckIsMostRecentPopulatedProduct isMostRecentPopulatedProduct(long productId) {
        ProductEntity mostRecentPopulatedProduct = productRepository
                .findFirst1ByProductStateOrderByCreatedDateDesc(ProductState.POPULATED);

        return CheckIsMostRecentPopulatedProduct.builder()
                .isMostRecentPopulatedProduct(mostRecentPopulatedProduct.getPid() == productId)
                .build();
    }

    public List<ProductSummaryForCatalog> getRecentlyPopulatedProductListAfter(GetPaginationCatalogRequest getPaginationCatalogRequest) {
        return productRepository
                .findByProductStateOrderByCreatedDateDesc(ProductState.POPULATED
                        , PageRequest.of(getPaginationCatalogRequest.getPage()
                                , getPaginationCatalogRequest.getCount()))
                .stream()
                .map((productEntity) -> toSummary(productEntity))
                .collect(Collectors.toList());
    }

    public List<ProductSummaryForCatalog> getBestProductList() {
        List<ProductEntity> bestProductEntityList = productRepository.findByUploaderId(ADMIN_ID);

        return bestProductEntityList
                .stream()
                .filter(productEntity -> {
                    return productEntity.getProductState() == ProductState.POPULATED;
                })
                .map(productEntity -> {
                    return toSummary(productEntity);
                })
                .collect(Collectors.toList());
    }

    private ProductSummaryForCatalog toSummary(ProductEntity productEntity) {
        ProductInformation information = productEntity.getProductInformation();

        return ProductSummaryForCatalog.builder()
                .pid(productEntity.getPid())
                .name(information.getName())
                .price(information.getPrice())
                .productImagePath(productEntity
                        .getProductImageInformation()
                        .getProductImagePath())
                .uploadedTime(productEntity.getCreatedDate())
                .build();
    }

    public List<ProductFullInformation> getProductList() {
        return productRepository.findAll()
                .stream()
                .map(productEntity -> {
                    ProductInformation information = productEntity.getProductInformation();
                    return ProductFullInformation.builder()
                            .productId(productEntity.getPid())
                            .productState(productEntity.getProductState())
                            .productImagePath(productEntity
                                    .getProductImageInformation()
                                    .getProductImagePath())
                            .foundProductInformation(FoundProductInformation.builder()
                                    .pid(productEntity.getPid())
                                    .categoryIdList(information.getCategory())
                                    .sizeList(information.getSize())
                                    .price(information.getPrice())
                                    .name(information.getName())
                                    .brand(information.getBrand())
                                    .build())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
