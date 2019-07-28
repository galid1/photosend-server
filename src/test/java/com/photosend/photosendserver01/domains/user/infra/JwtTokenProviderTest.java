package com.photosend.photosendserver01.domains.user.infra;

import com.photosend.photosendserver01.domains.user.domain.UserInformation;
import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import com.photosend.photosendserver01.util.token.JwtTokenProvider;
import com.photosend.photosendserver01.util.token.JwtTokenVerifier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
                .name("jjy")
                .passPortNum("123")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2099,12,12,12,30)))
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
