package com.photosend.photosendserver01.user.domain;

import com.photosend.photosendserver01.user.domain.exception.UploadTicketException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
        verifyTicketExist();
        this.ticket = Ticket.builder()
                    .ticketImagePath(imagePath)
                    .build();
    }

    private void verifyTicketExist() {
        if(ticket != null)
            throw new UploadTicketException("already ticket is exist");
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
