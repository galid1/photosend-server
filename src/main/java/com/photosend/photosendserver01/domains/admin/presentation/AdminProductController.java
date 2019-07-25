package com.photosend.photosendserver01.domains.admin.presentation;

import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundProductInformation;
import com.photosend.photosendserver01.domains.user.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminProductController {
    @Autowired
    private AdminProductService adminclothesService;

    @PostMapping("/{usersId}/product/information")
    public List<Long> putFoundClothesInformation(@PathVariable("usersId")String uid, @RequestBody List<FoundProductInformation> foundProductInformationList) {
        return adminclothesService.putFoundProductInformation(uid, foundProductInformationList);
    }
}
