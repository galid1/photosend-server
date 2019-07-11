package com.photosend.photosendserver01.domains.user.domain;

import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // UserEntity 저장 Test
    @Test
    public void testSaveUserEntity() {
        // given
        UserInformation information = UserInformation.builder()
                .name("asdasd")
                .age(10)
                .build();

        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                                                    .wechatUid("A")
                                                    .userInformation(information)
                                                    .build();

        // when
        userRepository.save(registerRequest.toEntity("A"));

        // then
        List<UserEntity> entities = userRepository.findAll();
        UserEntity getEntity = entities.get(0);
        assertThat(getEntity.getUserInformation().getName(), is(information.getName()));
    }

}
