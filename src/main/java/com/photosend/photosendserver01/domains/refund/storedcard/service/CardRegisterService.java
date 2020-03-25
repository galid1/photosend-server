package com.photosend.photosendserver01.domains.refund.storedcard.service;

import com.photosend.photosendserver01.domains.refund.storedcard.domain.CardRegistration;
import com.photosend.photosendserver01.domains.refund.storedcard.domain.StoredCardEntity;
import com.photosend.photosendserver01.domains.refund.storedcard.domain.StoredCardRepository;
import com.photosend.photosendserver01.domains.refund.usercard.domain.UserCardEntity;
import com.photosend.photosendserver01.domains.refund.usercard.domain.UserCardInformation;
import com.photosend.photosendserver01.domains.refund.usercard.domain.UserCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardRegisterService {
    @Autowired
    private StoredCardRepository storedCardRepository;
    @Autowired
    private UserCardRepository userCardRepository;

    @Transactional
    public void registerUser(long userId, CardRegisterRequest request) {
        StoredCardEntity storedCardEntity = storedCardRepository.findByCardInformation_CardNum(request.getCardNum())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드번호입니다."));

        long userCardId = createUserCard(request);

        storedCardEntity.registerUser(toCardRegistration(userId, userCardId, request));
    }

    private CardRegistration toCardRegistration(long userId, long userCardId, CardRegisterRequest request) {
        return CardRegistration.builder()
                .cardNum(request.getCardNum())
                .serial(request.getSerial())
                .userId(userId)
                .userCardId(userCardId)
                .build();
    }

    private long createUserCard(CardRegisterRequest request) {
        UserCardEntity cardEntity = UserCardEntity.builder()
                .userCardInformation(UserCardInformation.builder()
                        .cardNum(request.getCardNum())
                        .serial(request.getSerial())
                        .build())
                .build();

        return userCardRepository.save(cardEntity)
                .getUserCardId();
    }
}
