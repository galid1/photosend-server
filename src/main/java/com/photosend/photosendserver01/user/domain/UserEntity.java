package com.photosend.photosendserver01.user.domain;

import com.photosend.photosendserver01.user.domain.exception.ClothesException;
import com.photosend.photosendserver01.user.domain.exception.TicketException;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String wechatUid;

    // Required Field
    @Embedded
    @NonNull
    private UserInformation userInformation;

    // Optional Field
    @Embedded
    private Ticket ticket;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private List<ClothesEntity> clothesList = new ArrayList<>();

    @Builder
    public UserEntity(@NonNull String wechatUid, @NonNull UserInformation userInformation, Ticket ticket, List<ClothesEntity> clothesList) {
        this.wechatUid = wechatUid;
        this.userInformation = userInformation;
        this.ticket = ticket;
        this.clothesList = clothesList;
    }

    public void putTicketsImagePath(String ticketImagePath) {
        if(verifyTicketExist())
            throw new TicketException("이미 티켓이 존재합니다. 티켓은 하나만 업로드 가능합니다.");

        this.ticket = Ticket.builder()
                    .ticketImagePath(ticketImagePath)
                    .build();
    }

    // 티켓 이미지 변경 메소드 (변경시에는 티켓이 존재하지 않을 때 에러)
    public void modifyTicketsImagePath(String imagePath) {
        if(!verifyTicketExist())
            throw new TicketException("해당 티켓이 존재하지 않습니다.");

        verifyRequestModifyTicketImageInLimit();

        int modifyCount = 1;
        if(this.ticket.getTicketModifyCountPerThreeMinutes() != null)
            modifyCount = this.ticket.getTicketModifyCountPerThreeMinutes() + 1;

        this.ticket = Ticket.builder()
                    .ticketImagePath(imagePath)
                    .lastTicketImageModifyTime(Timestamp.valueOf(LocalDateTime.now()))
                    .ticketModifyCountPerThreeMinutes(modifyCount)
                    .build();
    }

    // 티켓 존재시 true , 아닐 시 false return
    private boolean verifyTicketExist() {
        return ticket != null ? true : false;
    }

    // 해당 유저가 3분 이내에 5번 초과의 요청을 했는지 검사
    private void verifyRequestModifyTicketImageInLimit() {
        if (this.ticket.getLastTicketImageModifyTime() != null && this.ticket.getTicketModifyCountPerThreeMinutes() != null) {
            resetTicketModifyCountPerThreeMinutes();

            if (this.ticket.getTicketModifyCountPerThreeMinutes() >= 5)
                throw new TicketException("3분마다 최대 5번까지만 요청이 가능합니다.");
        }
    }

    // 마지막 변경 요청 후 3분이 지나면 count 초기화
    private void resetTicketModifyCountPerThreeMinutes() {
        LocalDateTime lastModifyTime = this.ticket.getLastTicketImageModifyTime().toLocalDateTime();
        Duration duration = Duration.between(lastModifyTime, LocalDateTime.now());

        if (duration.toMinutes() > 3)
            this.ticket = Ticket.builder()
                    .ticketImagePath(this.ticket.getTicketImagePath())
                    .lastTicketImageModifyTime(Timestamp.valueOf(LocalDateTime.now()))
                    .ticketModifyCountPerThreeMinutes(0)
                    .build();
    }

    // 옷 사진의 이미지 경로 추가 메소드
    public void putClothesImagePath(ClothesEntity clothesEntity) {
        if(clothesList == null)
            this.clothesList = new ArrayList<>();

        if(clothesEntity == null)
            throw new ClothesException("ClothesEntity Null 입니다.");

        this.clothesList.add(clothesEntity);
    }

    // 옷 사진 제거 메소드
    public void deleteClothes(int clothesIndex) {
        verifyValidClothesIndex(clothesIndex);
        clothesList.remove(clothesIndex);
    }

    private void verifyValidClothesIndex(Integer clothesIndex) {
        if(clothesList.size() - 1 < clothesIndex)
            throw new IllegalArgumentException("리스트의 크기보다 Index가 더 큽니다.");
    }

}
