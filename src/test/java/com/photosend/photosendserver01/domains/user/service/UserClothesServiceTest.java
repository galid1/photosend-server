package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.*;
import com.photosend.photosendserver01.domains.user.infra.file.ImageType;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.ClothesImageUrl;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UidAndToken;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

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
    ClothesRepository clothesRepository;

    @Autowired
    private FileUtil fileUtil;

    private UidAndToken uidAndToken;
    private MockMultipartFile mockFile;

    @Before
    public void setUp() {
        UserInformation userInformation = UserInformation.builder().name("jjy").age(12).build();
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .wechatUid("A")
                .userInformation(userInformation)
                .build();

        uidAndToken = registerService.registerUser(userRegisterRequest);
        mockFile = new MockMultipartFile("clothesImage", "clothes.png", null, "lkjasdinzxcl image".getBytes());
    }


    // notEquals를 통해 랜덤으로 생성된 이름으로 업로드 되는지 테스트를 함(과 동시에 통과시 자연스럽게 업로드 테스트를 한것과 동일시 됨)
    @Test
    public void uploadClothesImageTest() {
        // when
        userClothesService.uploadClothesImages(uidAndToken.getUid(), new MultipartFile[]{mockFile});

        // then
        UserEntity userEntity = userRepository.findById(uidAndToken.getUid()).get();
        ClothesEntity clothesEntity = userEntity.getClothesList().get(0);
        String uploadPath = fileUtil.makeFileUploadPath(uidAndToken.getUid(), "clothesImage", ImageType.CLOTHES);
        assertNotEquals(uploadPath, clothesEntity.getClothesImagePath());
    }

//    @Test(expected = NoSuchElementException.class)
//    public void 이미지_삭제요청시_로컬에서_이미지삭제후_UserEntity의_ClothesEntity목록에서_제거_Test() {
//        // given
//        List<ClothesImageUrl> clothesImageUrlList = userClothesService.uploadClothesImages(uidAndToken.getUid(), new MultipartFile[]{mockFile});
//        Long clothesId = clothesImageUrlList.get(0).getCid();
//
//        // when
//        userClothesService.deleteClothesImage(uidAndToken.getUid(), clothesId);
//
//        // then
//        File file = new File(clothesImageUrlList.get(0).getClothesImageUrl());
//        assertFalse(file.exists());
//
//        // NoSuchElementException
//        ClothesEntity clothesEntity = clothesRepository.findById(clothesId).get();
//    }


}
