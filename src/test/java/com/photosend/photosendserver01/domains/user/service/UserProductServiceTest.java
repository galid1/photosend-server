package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.*;
import com.photosend.photosendserver01.domains.user.infra.file.ImageType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProductServiceTest {
    @Autowired
    UserProductService userProductService;

    @Autowired
    ProductRepository productRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    private FileUtil fileUtil;
    private static final String TEST_FILE_NAME = "FILE";
    // TEST 용으로 fileUtil의 makeFileUploadPath의 결과값을 아래 변수로 정함
    private static int FILE_NAME_INDEX = 0;
    private static final String TEST_FILE_PATH = System.getProperty("user.home") + "/" + ImageType.PRODUCT + "/" + FILE_NAME_INDEX;

    private MockMultipartFile mockFile;

    private Optional<UserEntity> userEntity;
    private static final String TEST_WECHAT_ID = "A";
    private static final String TEST_JWT_TOKEN = "TOKEN";

    //================ 셋업 =================//
    @Before
    public void setUp() {
        userEntity = Optional.ofNullable(UserEntity.builder()
                .wechatUid(TEST_WECHAT_ID)
                .userInformation(UserInformation.builder()
                        .name("jjy")
                        .build())
                .token(Token.builder()
                        .jwtToken(TEST_JWT_TOKEN)
                        .build())
                .build());

        mockFile = new MockMultipartFile("TEST", "TEST", null, "TEST TXT".getBytes());

        mockFileUtil();
        mockUserRepository();
    }

    private void mockUserRepository() {
        when(userRepository.findById(TEST_WECHAT_ID)).thenReturn(userEntity);
    }

    private void mockFileUtil() {
        when(fileUtil.makeFileUploadPath(TEST_WECHAT_ID, TEST_FILE_NAME, ImageType.PRODUCT))
                .thenAnswer(invocation -> {
                    FILE_NAME_INDEX ++;
                    return null;
                })
                .thenReturn(TEST_FILE_PATH);
    }

    //================ 이미지 업로드 테스트 =================//
    @Test
    public void uploadProductImageTest() {
//        // when
//        userClothesService.uploadProductImages(TEST_WECHAT_ID, new MultipartFile[]{ mockFile });
//
//        // then
//        assertTrue(FILE_NAME_INDEX == 1);
    }


    //================ 이미지 삭제 테스트 =================//
    // 이미지 제거시 스토리지에 없어야 하며, UserEntity의 ClothesList에서 제거 되어야함.
    @Test
    public void deleteProductImageTest() {

    }

}