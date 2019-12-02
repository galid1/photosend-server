package com.photosend.photosendserver01.domains.catalog.presentation;

import com.photosend.photosendserver01.domains.catalog.presentation.request_response.CheckIsMostRecentPopulatedProduct;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.GetPaginationCatalogRequest;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.ProductFullInformation;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.ProductSummaryForCatalog;
import com.photosend.photosendserver01.domains.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/{productId}")
    public CheckIsMostRecentPopulatedProduct checkIsMostRecentProduct(@PathVariable("productId") long productId) {
        return catalogService.isMostRecentPopulatedProduct(productId);
    }

    @GetMapping("/products")
    public List<ProductFullInformation> getProductList() {
        return catalogService.getProductInformationList();
    }

    @PostMapping("/products")
    public List<ProductSummaryForCatalog> getRecentlyPopulatedProductListAfter(@RequestBody GetPaginationCatalogRequest getPaginationCatalogRequest) {
        return catalogService.getRecentlyPopulatedProductListAfter(getPaginationCatalogRequest);
    }

    @GetMapping("/products/{productId}")
    public ProductFullInformation getProductInformation(@PathVariable("productId") long productId) {
        return catalogService.getProductInformation(productId);
    }

    @GetMapping("/products/best")
    public List<ProductSummaryForCatalog> getBestProductList() {
        return catalogService.getBestProductList();
    }
}
