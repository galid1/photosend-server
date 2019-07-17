package com.photosend.photosendserver01.domains.user.domain;

import com.photosend.photosendserver01.domains.user.domain.exception.TicketException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserEntityTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }
    }

    private static final String USER_WECHAT_ID = "A";
    private static final String JWT_TOKEN = "ASD";
    private UserEntity userEntity;

    @Mock
    private UserRepository userRepository;

    // ================ 셋업 ========================= //
    @Before
    public void setUp() {
        UserInformation information = UserInformation.builder()
                .name("jjy")
                .age(12)
                .build();

        userEntity = UserEntity.builder()
                .userInformation(information)
                .wechatUid(USER_WECHAT_ID)
                .token(Token.builder().jwtToken(JWT_TOKEN).build())

                .build();

        userRepositorySetUp();
    }

    public void userRepositorySetUp() {
        UserInformation information = UserInformation.builder()
                .name("jjy")
                .age(12)
                .build();

        Ticket ticket = Ticket.builder()
                .ticketImagePath("ASD")
                .lastTicketImageModifyTime(Timestamp.valueOf(LocalDateTime.now()))
                .ticketModifyCountPerThreeMinutes(0)
                .build();

        Optional<UserEntity> userEntityForModifyTest = Optional.ofNullable(UserEntity.builder()
                .userInformation(information)
                .ticket(ticket)
                .wechatUid(USER_WECHAT_ID)
                .token(Token.builder().jwtToken("A").build())
                .build());

        when(userRepository.findById(USER_WECHAT_ID)).thenReturn(userEntityForModifyTest);
    }

    // ===================== 티켓 업로드 테스트 ======================== //
    @Test
    public void putTicketsImagePathTest() {
        String ticketImagePath = "path";

        userEntity.putTicketsImagePath(ticketImagePath);
        assertTrue(userEntity.getTicket().getTicketImagePath().equals(ticketImagePath));
    }

    @Test(expected = TicketException.class)
    public void 이미_티켓이_존재하는경우_TicketException_테스트() {
        String ticketImagePath = "path";
        userEntity.putTicketsImagePath(ticketImagePath);
        userEntity.putTicketsImagePath(ticketImagePath);
    }

    // ===================== 티켓 수정 테스트 ========================= //
    @Test
    public void modifyTicketsImagePathTest() {
        String ticketImagePath = "path";
        String newPath = "new path";

        userEntity.putTicketsImagePath(ticketImagePath);
        userEntity.modifyTicketsImagePath(newPath);

        assertTrue(userEntity.getTicket().getTicketImagePath().equals(newPath));
    }

    @Test(expected = TicketException.class)
    public void 이미지_수정시_등록된_이미지가_없는경우_TicketException_테스트() {
        String newPath = "new path";
        userEntity.modifyTicketsImagePath(newPath);
    }

    @Test(expected = TicketException.class)
    public void 이미지_수정요청_3분이내_5번이상시_ClothesUploadCountException_테스트() {
        String newPath = "new Path";
        UserEntity userEntityForModifyTest = userRepository.findById(USER_WECHAT_ID).get();
        userEntityForModifyTest.modifyTicketsImagePath(newPath);
        userEntityForModifyTest.modifyTicketsImagePath(newPath);
        userEntityForModifyTest.modifyTicketsImagePath(newPath);
        userEntityForModifyTest.modifyTicketsImagePath(newPath);
        userEntityForModifyTest.modifyTicketsImagePath(newPath);
        userEntityForModifyTest.modifyTicketsImagePath(newPath);
    }

    // ===================== 옷 업로드 테스트 ===================== //
    @Test
    public void putClothesImagePathTest() {
        String clothesPath = "clothesPath";
        ClothesEntity clothesEntity = ClothesEntity.builder()
                .clothesImagePath(clothesPath)
                .build();

        userEntity.putClothesImagePath(clothesEntity);

        assertTrue(clothesPath.equals(userEntity.getClothesList().get(0).getClothesImagePath()));
    }

    // ===================== 옷 삭제 테스트 ====================== //
    @Test
    public void deleteClothesTest() {

    }
}
