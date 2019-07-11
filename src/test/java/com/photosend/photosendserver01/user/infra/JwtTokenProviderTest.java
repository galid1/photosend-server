package com.photosend.photosendserver01.user.infra;

import com.photosend.photosendserver01.user.domain.UserEntity;
import com.photosend.photosendserver01.user.domain.UserInformation;
import com.photosend.photosendserver01.user.domain.UserRepository;
import com.photosend.photosendserver01.user.presentation.request_reponse.UserRegisterRequest;
import com.photosend.photosendserver01.user.service.JwtTokenProvider;
import com.photosend.photosendserver01.user.service.JwtTokenVerifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private JwtTokenVerifier verifier;
    @Autowired
    private UserRepository userRepository;

    private String uid;
    private String jwtToken;

    @Before
    public void setUp() {
        uid = "A";
        jwtToken = tokenProvider.createToken(uid);
    }

    @Test
    public void createTokenTest() {
        assertTrue(!jwtToken.isEmpty());
    }

    @Test
    public void verifyToken() {
        UserInformation information = UserInformation.builder()
                .age(12)
                .name("jjy")
                .build();

        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                .wechatUid(uid)
                .userInformation(information)
                .build();

        userRepository.save(registerRequest.toEntity(jwtToken));

        verifier.verifyToken(uid, jwtToken);
    }

//    @After
//    public void _finally() {
//        userRepository.deleteAll();
//    }
}
