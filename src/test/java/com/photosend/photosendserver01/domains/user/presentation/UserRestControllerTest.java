package com.photosend.photosendserver01.domains.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosend.photosendserver01.domains.user.domain.UserInformation;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objMapper;

    @Test
    public void testRegisterUser() throws Exception {
        // given
        UserInformation information = UserInformation.builder()
                .age(12)
                .name("asd")
                .build();

        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                .userInformation(information)
                .wechatUid("A")
                .build();

        // when, then
        mockMvc.perform(post("/users/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objMapper.writeValueAsString(registerRequest))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
