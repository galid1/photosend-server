package com.photosend.photosendserver01.domains.refund.storedcard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "stored_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StoredCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long storedCardId;

    @Embedded
    private StoredCardInformation cardInformation;

    @Enumerated(value = EnumType.STRING)
    private StoredCardState cardState;

    private Long ownerId;
    private Long userCardId;

    @Builder
    public StoredCardEntity(StoredCardInformation cardInformation) {
        this.cardInformation = cardInformation;
        cardState = StoredCardState.UNREGISTERED;
    }

    public void registerUser(CardRegistration cardRegistration) {
        this.verifyRegistrableState();
        this.verifyRegistration(cardRegistration);

        this.ownerId = cardRegistration.getUserId();
        this.userCardId = cardRegistration.getUserCardId();
        this.cardState = StoredCardState.REGISTERED;
    }

    private void verifyRegistrableState() {
        if(this.cardState != StoredCardState.UNREGISTERED)
            throw new IllegalStateException("이미 등록되었거나, 분실상태의 카드입니다.");
    }

    private void verifyRegistration(CardRegistration cardRegistration) {
        if(! cardRegistration.getSerial().equals(this.cardInformation.getSerial()))
            throw new IllegalArgumentException("serial 번호가 일치하지 않습니다.");
    }

    public void initCard() {
        this.verifyRegistered();

        this.cardInformation.renewSerial();
        this.ownerId = null;
        this.userCardId = null;
        this.cardState = StoredCardState.UNREGISTERED;
    }

    public void lostCard() {
        this.verifyRegistered();

        this.cardState = StoredCardState.LOST;
    }

    private void verifyRegistered() {
        if(this.cardState != StoredCardState.REGISTERED)
            throw new IllegalArgumentException("카드가 등록된 상태가 아닙니다.");
    }
}
