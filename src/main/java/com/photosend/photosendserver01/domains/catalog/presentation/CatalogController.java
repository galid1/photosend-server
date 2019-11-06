package com.photosend.photosendserver01.domains.catalog.presentation;

import com.photosend.photosendserver01.domains.catalog.presentation.request_response.CheckIsMostRecentPopulatedProduct;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.GetRecentlyPopulatedProductRequest;
import com.photosend.photosendserver01.domains.catalog.service.CatalogService;
import com.photosend.photosendserver01.domains.catalog.service.ProductSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/products")
    public List<ProductSummary> getRecentlyPopulatedProductListAfter(@RequestBody GetRecentlyPopulatedProductRequest getRecentlyPopulatedProductRequest) {
        return catalogService.getRecentlyPopulatedProductListAfter(getRecentlyPopulatedProductRequest);
    }

    @PostMapping("/{productId}")
    public CheckIsMostRecentPopulatedProduct checkIsMostRecentProduct(@PathVariable("productId") long productId) {
        return catalogService.isMostRecentPopulatedProduct(productId);
    }
}
