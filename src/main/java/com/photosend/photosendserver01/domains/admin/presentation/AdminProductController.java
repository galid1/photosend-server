package com.photosend.photosendserver01.domains.admin.presentation;

import com.photosend.photosendserver01.domains.admin.service.AdminProductService;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundProductInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminProductController {
    @Autowired
    private AdminProductService adminProductService;

    @PostMapping("/admin/{usersId}/product/information")
    public void inputProductInformation(@PathVariable("usersId") long userId, List<FoundProductInformation> foundProductInformationList) {
        adminProductService.inputProductInformation(userId, foundProductInformationList);
    }
}
