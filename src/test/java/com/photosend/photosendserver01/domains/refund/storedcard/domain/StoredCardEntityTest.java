package com.photosend.photosendserver01.domains.refund.storedcard.domain;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoredCardEntityTest {
    @Autowired
    private StoredCardRepository storedCardRepository;

    private String TEST_RIGHT_CARD_NUM = "1111111111111111";
    private String TEST_WRONG_CARD_NUM = "2222";

    private String TEST_WRONG_SERIAL = "AAAA";

    private StoredCardEntity savedEntity = null;

    @Before
    public void init() {
        StoredCardEntity entity = StoredCardEntity.builder()
                .cardInformation(StoredCardInformation.builder()
                        .cardNum(TEST_RIGHT_CARD_NUM)
                        .build()
                )
                .build();

        savedEntity = storedCardRepository.save(entity);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void whenCardNumLengthIsNot16ThrowError() throws Exception {
        //given, when, then
        StoredCardEntity.builder()
                .cardInformation(StoredCardInformation.builder()
                        .cardNum(TEST_WRONG_CARD_NUM)
                        .build()
                )
                .build();
    }

    @Test
    @Transactional
    public void whenRenewSerialCreatedNotDuplicatedSerial() throws Exception {
        //when
        String oldSerial = savedEntity.getCardInformation().getSerial();
        savedEntity.getCardInformation().renewSerial();
        String newSerial = savedEntity.getCardInformation().getSerial();

        //then
        Assertions.assertThat(oldSerial).isNotEqualTo(newSerial);
    }

    @Test
    @Transactional
    public void whenRegisterUserTest() throws Exception {
        String serial = savedEntity.getCardInformation().getSerial();

        CardRegistration cardRegistration = CardRegistration.builder()
                .userCardId(1l)
                .userId(1l)
                .cardNum(TEST_RIGHT_CARD_NUM)
                .serial(serial)
                .build();

        //when
        savedEntity.registerUser(cardRegistration);

        //then
        Assertions.assertThat(savedEntity.getOwnerId()).isNotEqualTo(null);
        Assertions.assertThat(savedEntity.getUserCardId()).isNotEqualTo(null);
        Assertions.assertThat(savedEntity.getCardState()).isEqualTo(StoredCardState.REGISTERED);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void whenRegisterUserWithWrongSerialThenThrowException() throws Exception {
        CardRegistration cardRegistration = CardRegistration.builder()
                .userCardId(1l)
                .userId(1l)
                .cardNum(TEST_RIGHT_CARD_NUM)
                .serial(TEST_WRONG_SERIAL)
                .build();

        //when
        savedEntity.registerUser(cardRegistration);
    }

    @Test
    @Transactional
    public void whenReturnCardThenCardStateIsUNREGISTERED() throws Exception {
        //given
        String serial = savedEntity.getCardInformation().getSerial();
        CardRegistration cardRegistration = CardRegistration.builder()
                .userCardId(1l)
                .userId(1l)
                .cardNum(TEST_RIGHT_CARD_NUM)
                .serial(serial)
                .build();

        savedEntity.registerUser(cardRegistration);

        //when
        savedEntity.initCard();

        //then
        Assertions.assertThat(savedEntity.getCardState()).isEqualTo(StoredCardState.UNREGISTERED);
    }

}