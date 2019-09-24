package com.photosend.photosendserver01.domains;

import com.photosend.photosendserver01.domains.user.domain.Token;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserInformation;
import com.photosend.photosendserver01.domains.user.domain.exception.DepartureTimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserEntityTest {
    @Test(expected = DepartureTimeException.class)
    public void 출국_10시20시_사이_이외시간_가입_불가_test() {
        UserInformation information =
                UserInformation.builder()
                        .departureTime(Timestamp.valueOf(LocalDateTime.now()))
                        .passPortNum("123")
                        .name("jjy")
                        .build();

        UserEntity userEntity
                = UserEntity.builder()
                .weChatOpenId("A")
                .userInformation(information)
                .token(Token.builder().jwtToken("ASD").build())
                .build();
    }
}
