package com.photosend.photosendserver01.domains.user.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;


    private String TEST_WECHAT_UID = "A";
    private ProductEntity testProductEntity;

    @Before
    public void setUp() {
        UserEntity testUserEntity = UserEntity.builder()
                .wechatUid(TEST_WECHAT_UID)
                .userInformation(UserInformation.builder()
                        .name("CCC")
                        .passPortNum("123123")
                        .departureTime(Timestamp.valueOf("2099-07-04 12:00:00"))
                        .build())
                .token(Token.builder().jwtToken("A").build())
                .build();

        userRepository.save(testUserEntity);

        testProductEntity = ProductEntity.builder()
                .productImagePath("ASDSADAS")
                .userEntity(testUserEntity)
                .productInformation(ProductInformation.builder()
                        .price(1122)
                        .size(Collections.singletonList(Size.LARGE))
                        .brand("A")
                        .name("DDSDA")
                        .build())
                .productLocation(ProductLocation.builder()
                        .longitude(1.11f)
                        .latitude(1.11f)
                        .build())
                .build();

        productRepository.save(testProductEntity);
    }


    @Test
    public void Product_상태가_Ordered가_아닌_것을_불러옴_Test() {
        ProductEntity findEntity = productRepository.findByPidAndUserWechatUidAndProductStateNot(1l, TEST_WECHAT_UID, ProductState.ORDERED);
        assertEquals(findEntity.getProductImagePath(), testProductEntity.getProductImagePath());
    }


    @After
    public void clear() {
        productRepository.deleteAll();
    }

}
