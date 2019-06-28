package com.photosend.photosendserver01.user.service;

import com.photosend.photosendserver01.user.domain.ClothesEntity;
import com.photosend.photosendserver01.user.domain.UserEntity;
import com.photosend.photosendserver01.user.domain.UserInformation;
import com.photosend.photosendserver01.user.domain.UserRepository;
import com.photosend.photosendserver01.user.infra.file.ImageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    UploadFileUtil uploadFileUtil;

    // notEquals를 통해 랜덤으로 생성된 이름으로 업로드 되는지 테스트를 함(과 동시에 통과시 자연스럽게 업로드 테스트를 한것과 동일시 됨)
    @Test
    public void uploadClothesImageTest() {
        // given
        Long userId = registerService.registerUser(UserInformation.builder().name("jjy").age(12).build());
        String fileName = "file1";
        MockMultipartFile file1 = new MockMultipartFile(fileName, "test.txt", null, "test data.".getBytes());

        // when
        userClothesService.uploadClothesImages(userId, new MultipartFile[]{file1});

        // then
        UserEntity userEntity = userRepository.findById(userId).get();
        ClothesEntity clothesEntity = userEntity.getClothesList().get(0);
        String uploadPath = uploadFileUtil.makeFileUploadPath(userId, fileName, ImageType.CLOTHES);
        assertNotEquals(uploadPath, clothesEntity.getClothesImagePath());
    }


}
