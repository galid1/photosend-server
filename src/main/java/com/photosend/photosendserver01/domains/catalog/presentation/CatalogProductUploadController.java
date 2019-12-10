package com.photosend.photosendserver01.domains.catalog.presentation;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductLocation;
import com.photosend.photosendserver01.domains.catalog.infra.ImageType;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.ProductUploadResponse;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.UploadedProduct;
import com.photosend.photosendserver01.domains.catalog.service.CatalogProductUploadService;
import com.photosend.photosendserver01.domains.catalog.service.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogProductUploadController {
    @Autowired
    private CatalogProductUploadService catalogProductUploadService;

    @Autowired
    private FileUtil fileUtil;

    @PostMapping(value = "/products/users/{userId}/pictures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ProductUploadResponse> uploadProductImages(@PathVariable("userId") long userId,
                                                           @RequestParam("location") ProductLocation[] productLocations,
                                                           @RequestParam("file") MultipartFile[] productImageFiles) throws IOException {
        verifyAtLeastOneProductImageInfo(productImageFiles, productLocations);
        verifyCountIsEqual(productImageFiles.length, productLocations.length);

        return catalogProductUploadService
                .uploadProductImages(userId, makeUploadedProductList(productLocations, productImageFiles));
    }

    private void verifyAtLeastOneProductImageInfo(MultipartFile[] productImageFiles, ProductLocation[] productLocations) {
        if(productImageFiles.length < 1 || productLocations.length < 1)
            throw new IllegalArgumentException("상품의 이미지 또는 상품의 위치정보가 존재하지 않습니다.");
    }

    private void verifyCountIsEqual(int imageFilesCount, int locationInformationCount) {
        if(imageFilesCount != locationInformationCount)
            throw new IllegalArgumentException("이미지의 개수와 위치정보의 개수가 다릅니다.");
    }

    private List<UploadedProduct> makeUploadedProductList(ProductLocation[] productLocations, MultipartFile[] productImageFiles) throws IOException {
        List<UploadedProduct> uploadedProductList = new ArrayList<>();

        for (int i = 0; i < productLocations.length; i++) {
            uploadedProductList.add(
                    UploadedProduct.builder()
                    .imageBytes(productImageFiles[i].getBytes())
                    .uploadedImageFilePath(makeFilePath(productImageFiles[i].getOriginalFilename()))
                    .productLocation(productLocations[i])
                    .build());
        }

        return uploadedProductList;
    }

    private String makeFilePath(String originalFileName) {
        return fileUtil.makeFileUploadPath(originalFileName, ImageType.PRODUCT);
    }

}
