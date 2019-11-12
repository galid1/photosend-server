package com.photosend.photosendserver01.domains.admin.presentation;

import com.photosend.photosendserver01.domains.admin.service.AdminProductInformationService;
import com.photosend.photosendserver01.domains.catalog.presentation.request_response.FoundProductInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminProductInformationController {
    @Autowired
    private AdminProductInformationService adminProductInformationService;

    @PostMapping("/admin/products/information")
    public void uploadProductListInformation(@RequestBody List<FoundProductInformation> foundProductInformationList) {
        adminProductInformationService.inputProductInformation(foundProductInformationList);
    }
}
