package com.photosend.photosendserver01.user.domain;

import com.photosend.photosendserver01.user.domain.exception.TicketException;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uid;

    // Required Field
    @Embedded
    @NonNull
    private UserInformation userInformation;

    // Optional Field
    @Embedded
    private Ticket ticket;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private List<ClothesEntity> clothesList = new ArrayList<>();

    @Builder
    public UserEntity(@NonNull UserInformation userInformation, Ticket ticket, List<ClothesEntity> clothList) {
        this.userInformation = userInformation;
        this.ticket = ticket;
        this.clothesList = clothList;
    }

    public void putTicketsImagePath(String imagePath) {
        if(verifyTicketExist())
            throw new TicketException("already ticket exist");

        this.ticket = Ticket.builder()
                    .ticketImagePath(imagePath)
                    .build();
    }

    // 변경시에는 티켓이 존재하지 않을 때 에러
    public void modifyTicketsImagePath(String imagePath) {
        if(!verifyTicketExist())
            throw new TicketException("ticket doesn't exist");

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
                throw new TicketException("can only request five times per three minutes!");
        }
    }

    // 마지막 변경 요청 후 3분이 지나면 count 초기화
    private void resetTicketModifyCountPerThreeMinutes() {
        // 3분 계산 방식 바꿔야 함
        int lastModifyMinutes = this.ticket.getLastTicketImageModifyTime().toLocalDateTime().getMinute();
        int curMinutes = LocalDateTime.now().getMinute();

        int subMinutes = Math.abs(lastModifyMinutes - curMinutes);

        if (subMinutes > 3)
            this.ticket = Ticket.builder()
                    .ticketImagePath(this.ticket.getTicketImagePath())
                    .lastTicketImageModifyTime(Timestamp.valueOf(LocalDateTime.now()))
                    .ticketModifyCountPerThreeMinutes(0)
                    .build();
    }

    public void uploadCloth(ClothesEntity cloth) {
        if(clothesList == null)
            clothesList = new ArrayList<>();

        if(cloth != null)
            clothesList.add(cloth);
    }

    public int getLastUploadClothIndex() {
        if (this.clothesList.size() == 0)
            throw new IllegalStateException("업로드된 Clothes가 없습니다.");

        return clothesList.size();
    }
}
