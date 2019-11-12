package com.photosend.photosendserver01.domains.catalog.presentation;

import com.photosend.photosendserver01.domains.catalog.presentation.request_response.CheckIsMostRecentPopulatedProduct;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.GetRecentlyPopulatedProductRequest;
import com.photosend.photosendserver01.domains.catalog.service.CatalogService;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.ProductSummary;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.ProductFullInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogRecentlyUploadedController {

    @Autowired
    private CatalogService catalogService;

    @PostMapping("/products")
    public List<ProductSummary> getRecentlyPopulatedProductListAfter(@RequestBody GetRecentlyPopulatedProductRequest getRecentlyPopulatedProductRequest) {
        return catalogService.getRecentlyPopulatedProductListAfter(getRecentlyPopulatedProductRequest);
    }

    @PostMapping("/{productId}")
    public CheckIsMostRecentPopulatedProduct checkIsMostRecentProduct(@PathVariable("productId") long productId) {
        return catalogService.isMostRecentPopulatedProduct(productId);
    }

    @GetMapping("/products")
    public List<ProductFullInformation> getProductList() {
        return catalogService.getProductList();
    }
}
