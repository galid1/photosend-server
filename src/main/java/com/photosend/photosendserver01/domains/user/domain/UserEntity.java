package com.photosend.photosendserver01.domains.user.domain;

import com.photosend.photosendserver01.domains.user.domain.exception.ProductException;
import com.photosend.photosendserver01.domains.user.domain.exception.ProductUploadCountException;
import com.photosend.photosendserver01.domains.user.domain.exception.TicketException;
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

    @Embedded
    @NonNull
    private Token token;

    // Optional Field
    @Embedded
    private Ticket ticket;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private List<ProductEntity> productList = new ArrayList<>();

    @Builder
    public UserEntity(@NonNull String wechatUid, @NonNull UserInformation userInformation, @NonNull Token token, Ticket ticket) {
        this.wechatUid = wechatUid;
        this.userInformation = userInformation;
        this.token = token;
        this.ticket = ticket;
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
            throw new TicketException("업로드된 티켓이 존재하지 않습니다.");

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

    // 상품 사진의 이미지 경로 추가 메소드
    public void putProductImagePath(ProductEntity productEntity) {
        verifyProductCountFive();

        if(productEntity == null)
            throw new ProductException("ProductEntity Null 입니다.");

        this.productList.add(productEntity);
    }

    // 옷 사진 최대 5개 업로드 가능 제한 로직
    private void verifyProductCountFive() {
        if (this.productList.size() >= 5)
            throw new ProductUploadCountException("옷 이미지는 최대 5장 업로드 가능합니다.");
    }

    // 옷 사진 제거 메소드 (로깅의 이유로 사진 삭제 X)
    public void deleteProduct(int productIndex) {
        verifyValidProductIndex(productIndex);
        productList.remove(productIndex);
    }

    private void verifyValidProductIndex(Integer productIndex) {
        if(productList.size() - 1 < productIndex)
            throw new IllegalArgumentException("리스트의 크기보다 Index가 더 큽니다.");
    }
}
