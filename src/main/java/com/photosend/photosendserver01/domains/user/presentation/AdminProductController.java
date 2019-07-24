package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundProductInformation;
import com.photosend.photosendserver01.domains.user.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminProductController {
    @Autowired
    private AdminProductService adminclothesService;

    @PostMapping("/users/{users-id}/product/information")
    public List<Long> putFoundClothesInformation(@PathVariable("users-id")String uid, @RequestBody List<FoundProductInformation> foundProductInformationList) {
        return adminclothesService.putFoundProductInformation(uid, foundProductInformationList);
    }
}
