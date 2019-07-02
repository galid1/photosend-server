package com.photosend.photosendserver01.user.presentation;

import com.photosend.photosendserver01.user.service.UserClothesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserClothesController {

    @Autowired
    private UserClothesService userClothesService;

    @PostMapping(value = "/{users-id}/clothes/pictures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ClothesImageUrl> uploadClothesImages(@PathVariable("users-id") Long userId, @RequestParam("file") MultipartFile[] clothesImageFiles) {
        return userClothesService.uploadClothesImages(userId, clothesImageFiles);
    }

    @DeleteMapping(value = "/{users-id}/clothes/{clothes-id}")
    public void deleteClothes(@PathVariable("users-id") Long userId, @PathVariable("clothes-id") Long clothesId) {
        userClothesService.deleteClothesImage(userId, clothesId);
    }

}
