package com.photosend.photosendserver01.domains.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosend.photosendserver01.domains.user.domain.Token;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objMapper;

    @Test
    public void userRegisterTest() throws Exception {
        // given
        UserInformation userInformation = UserInformation.builder()
                .name("jjy")
                .age(12)
                .build();

        UserEntity entity = UserEntity.builder()
                .wechatUid("A")
                .userInformation(userInformation)
                .token(Token.builder().jwtToken("ASD").build())
                .build();

        String objString = objMapper.writeValueAsString(entity);
        System.out.println(objString);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("X-JWT"));
    }
}
