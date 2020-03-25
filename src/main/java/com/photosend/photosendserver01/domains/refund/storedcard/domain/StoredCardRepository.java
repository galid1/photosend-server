package com.photosend.photosendserver01.domains.refund.storedcard.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoredCardRepository extends JpaRepository<StoredCardEntity, Long> {
    Optional<StoredCardEntity> findByCardInformation_CardNum(String cardNum);
}
