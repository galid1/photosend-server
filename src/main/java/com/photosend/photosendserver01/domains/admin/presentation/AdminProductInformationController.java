package com.photosend.photosendserver01.domains.admin.presentation;

import com.photosend.photosendserver01.domains.admin.service.AdminProductInformationService;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundProductInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminProductInformationController {
    @Autowired
    private AdminProductInformationService adminProductInformationService;

    @PostMapping("/admin/{usersId}/products/information")
    public void uploadProductListInformation(@PathVariable("usersId")long userId, @RequestBody List<FoundProductInformation> foundProductInformationList) {
        adminProductInformationService.inputProductInformation(userId, foundProductInformationList);
    }
}
