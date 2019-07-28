package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.UserInformation;
import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.domain.exception.TicketException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
                .name("jjy")
                .passPortNum("123")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2099,12,12,12,30)))
                .build();

        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                                                .wechatUid(uid)
                                                .userInformation(information)
                                                .build();
        repository.save(registerRequest.toEntity("A"));
    }

    @Test(expected = TicketException.class)
    public void 이미지수정_요청시_존재하는이미지가_없다면_TicketException_Test() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("A", "A", null, "ASD".getBytes());
        userTicketService.modifyTicketImage(uid, mockMultipartFile);
    }
}
