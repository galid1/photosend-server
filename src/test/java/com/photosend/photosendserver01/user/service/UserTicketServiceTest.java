package com.photosend.photosendserver01.user.service;

import com.photosend.photosendserver01.user.domain.UserInformation;
import com.photosend.photosendserver01.user.domain.UserRepository;
import com.photosend.photosendserver01.user.domain.exception.TicketException;
import com.photosend.photosendserver01.user.presentation.request_reponse.UserRegisterRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTicketServiceTest {
    @Autowired
    private UserTicketService userTicketService;

    @Autowired
    private UserRepository repository;

    private String uid = "A";

    @Before
    public void setUp() {
        UserInformation information = UserInformation.builder()
                .age(12)
                .name("jjy")
                .build();

        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                                                .wechatUid(uid)
                                                .userInformation(information)
                                                .build();
        repository.save(registerRequest.toEntity("A"));
    }

    @Test(expected = TicketException.class)
    public void modifyTicketTest() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("A", "A", null, "ASD".getBytes());
        userTicketService.modifyTicketImage(uid, mockMultipartFile);
    }
}
