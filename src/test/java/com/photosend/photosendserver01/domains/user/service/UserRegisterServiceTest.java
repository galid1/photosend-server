package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserInformation;
import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.domain.exception.UserDuplicatedException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import com.photosend.photosendserver01.util.token.JwtTokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRegisterServiceTest {

    private static final String TEST_WECHAT_ID = "A";
    private static final String TEST_JWT_TOKEN = "A";

    private UserInformation information;
    private UserRegisterRequest registerRequest;
    private Optional<UserEntity> userEntity;

    @Autowired
    private UserRegisterService registerService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    // ================= 셋업 ===================//
    @Before
    public void setUp() {
        information = UserInformation.builder()
                .name("jjy")
                .age(12)
                .build();

        registerRequest = UserRegisterRequest.builder()
                .wechatUid(TEST_WECHAT_ID)
                .userInformation(information)
                .build();

        userEntity = Optional.ofNullable(registerRequest.toEntity(TEST_JWT_TOKEN));

        mockJwtTokenProvider();
        mockUserRepository();
    }

    private void mockJwtTokenProvider() {
        when(jwtTokenProvider.createToken(TEST_WECHAT_ID)).thenReturn(TEST_JWT_TOKEN);
    }

    private void mockUserRepository() {
        when(userRepository.findById(TEST_WECHAT_ID)).thenReturn(userEntity);
    }

    // ================= 유저 등록 테스트 =============== //
    @Test
    public void registerUserTest() {
        // when
        registerService.registerUser(registerRequest);

        // then
        UserEntity getEntity = userRepository.findById(TEST_WECHAT_ID).get();
        assertTrue(getEntity.getUserInformation().getName().equals(information.getName()));
        assertTrue(getEntity.getToken().getJwtToken().equals(TEST_JWT_TOKEN));
    }

    @Test(expected = UserDuplicatedException.class)
    public void registerUser_중복체크_테스트() {
        // when
        registerService.registerUser(registerRequest);
        registerService.registerUser(registerRequest);
    }

}
