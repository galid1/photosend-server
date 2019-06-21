package com.photosend.photosendserver01.user.service;

import com.photosend.photosendserver01.user.domain.*;
import com.photosend.photosendserver01.user.service.users.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClothesRepository clothRepository;
    @Autowired
    UserService userService;

    // UserEntity에 clothes 업로드 Test
    @Test
    public void testUploadCloth() {
        //given
        UserInformation information = UserInformation.builder()
                .name("asdasd")
                .age(10)
                .build();

        UserEntity entity = UserEntity.builder()
                .userInformation(information)
                .build();

        userRepository.save(entity);

        ClothesEntity cloth = ClothesEntity.builder()
                .clothImagePath("http://..")
                .brand("a")
                .name("aasd")
                .price(111)
                .size(Size.MEDIUM)
                .build();

        ClothesEntity cloth2 = ClothesEntity.builder()
                .clothImagePath("http://..")
                .brand("ssa")
                .name("aasdd")
                .price(1211)
                .size(Size.LARGE)
                .build();

        clothRepository.save(cloth);
        clothRepository.save(cloth2);


        // when
        List<UserEntity> userEntities = userRepository.findAll();
        UserEntity userEntity = userEntities.get(0);
        List<ClothesEntity> clothEntities = clothRepository.findAll();

        userService.uploadClothImage(0l, cloth);
        userService.uploadClothImage(0l, cloth2);

        //then
        assertThat(userEntity.getClothesList().get(0).getClothImagePath(), is(cloth.getClothImagePath()));
        assertThat(userEntity.getClothesList().get(1).getClothImagePath(), is(cloth2.getClothImagePath()));
    }
}
