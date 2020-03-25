package com.photosend.photosendserver01.domains.refund.usercard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userCardId;

    @Embedded
    private UserCardInformation userCardInformation;

    @ElementCollection
    @CollectionTable(name = "usage_history",
    joinColumns = @JoinColumn(name = "user_card_id"))
    private List<UsageHistory> usageHistoryList;

    @Enumerated(EnumType.STRING)
    private UserCardState cardState;

    @Builder
    public UserCardEntity(UserCardInformation userCardInformation) {
        this.userCardInformation = userCardInformation;
        this.cardState = UserCardState.REGISTERED;
        this.usageHistoryList = new ArrayList<>();
    }

    public void returnCard() {

    }

    public void lostCard() {

    }

    private void isRegistered() {

    }
}
