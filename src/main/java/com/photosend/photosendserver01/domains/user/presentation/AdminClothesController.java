package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundClothesInformation;
import com.photosend.photosendserver01.domains.user.service.AdminClothesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminClothesController {
    @Autowired
    private AdminClothesService adminclothesService;

    @PostMapping("/users/{users-id}/clothes/information")
    public void putFoundClothesInformation(@PathVariable("users-id")String uid, @RequestBody List<FoundClothesInformation> foundClothesInformationList) {
        adminclothesService.putFoundClothesInformation(uid, foundClothesInformationList);
    }
}
