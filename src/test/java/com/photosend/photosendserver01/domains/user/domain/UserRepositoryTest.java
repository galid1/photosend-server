package com.photosend.photosendserver01.domains.user.domain;

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
    @Autowired
    private ClothesRepository clothRepository;

    // UserEntity 저장 Test
    @Test
    public void testSaveUserEntity() {
        // given
        UserInformation information = UserInformation.builder()
                .name("asdasd")
                .age(10)
                .build();

        UserEntity entity = UserEntity.builder()
                .userInformation(information)
                .build();

        // when
        userRepository.save(entity);

        // then
        List<UserEntity> entities = userRepository.findAll();
        UserEntity getEntity = entities.get(0);
        assertThat(getEntity.getUserInformation().getName(), is(information.getName()));
    }

}
