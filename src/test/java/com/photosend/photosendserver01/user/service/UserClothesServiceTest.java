package com.photosend.photosendserver01.user.service;

import com.photosend.photosendserver01.user.domain.*;
import com.photosend.photosendserver01.user.presentation.ClothesImageUrl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserClothesServiceTest {

    @Autowired
    UserRegisterService registerService;

    @Autowired
    UserClothesService userClothesService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void uploadClothesImageTest() {
        // given
        Long userId = registerService.registerUser(UserInformation.builder().name("jjy").age(12).build());

        List<ClothesImageUrl> clothesImageUrlList = new ArrayList<>();
        clothesImageUrlList.add(ClothesImageUrl.builder().clothesImageUrl("a").build());
        clothesImageUrlList.add(ClothesImageUrl.builder().clothesImageUrl("b").build());
        clothesImageUrlList.add(ClothesImageUrl.builder().clothesImageUrl("c").build());

        // when
        userClothesService.uploadClothesImages(userId, clothesImageUrlList);

        // then
        List<ClothesEntity> clothesList = userRepository.findById(userId).get().getClothesList();
        assertEquals(clothesList.get(0).getClothesImagePath(), "a");
        assertEquals(clothesList.get(1).getClothesImagePath(), "b");
        assertEquals(clothesList.get(2).getClothesImagePath(), "c");
    }


}
