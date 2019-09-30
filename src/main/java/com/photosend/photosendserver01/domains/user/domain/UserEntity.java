package com.photosend.photosendserver01.domains.user.domain;

import com.photosend.photosendserver01.common.config.logging.BaseTimeEntity;
import com.photosend.photosendserver01.domains.user.domain.exception.*;
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
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "wechat_openid")
    private String weChatOpenId;

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
    public UserEntity(@NonNull String weChatOpenId, @NonNull UserInformation userInformation, @NonNull Token token, Ticket ticket) {
        this.weChatOpenId = weChatOpenId;
        setUserInformation(userInformation);
        this.token = token;
        this.ticket = ticket;
    }

    private void setUserInformation(UserInformation userInformation) {
        if(userInformation == null) {
            throw new IllegalArgumentException("UserInformation을 입력하세요.");
        }
//        if(userInformation.getPassPortNum() == null) {
//            throw new IllegalArgumentException("请至少输入一个字以上。(请输入护照号码。)"); //여권 번호를 입력하세요
//        }
        if(userInformation.getDepartureTime() == null) {
            throw new IllegalArgumentException("출국 시간을 입력하세요.");
        }

        verifyDepartureTime(userInformation.getDepartureTime().toLocalDateTime());
        this.userInformation = userInformation;
    }

    // 출국 시간 제한 (출국은 최소 하루전, 10시에서 8시 사이만 가능)
    @Transient
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private int registerMinTime = 10;
    @Transient
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private int registerMaxTime = 20;

    private void verifyDepartureTime(LocalDateTime departureTime) {
        LocalDateTime now = LocalDateTime.now();
        // 가입 시간 제한 최소 출국 하루 전
        if(Duration.between(now, departureTime).toDays() < 1)
            throw new DepartureTimeException("为了方便配送,至少在出境前一天可以加入."); // "원활한 배송을 위해 최소 출국하루전에만 사용이 가능합니다"

        // 가입 시간 제한 (배송을 위해서 오전10시에서 오후8시 사이에 출국하는 사람에 한해서만 사용가능)
        int departureHour = departureTime.getHour();
        if(departureHour < registerMinTime || departureHour > registerMaxTime)
            throw new DepartureTimeException("为了确保顺畅地配送,仅允许上午10点至下午8点出境的用户使用."); // "원활한 배송을 위해 오전 10시에서 오후 8시 사이에 출국하는 사용자만 이용 가능합니다."
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
        verifyUploadProductImageTime(this.userInformation.getDepartureTime().toLocalDateTime());
        this.productList.add(productEntity);
    }

    private void verifyUploadProductImageTime(LocalDateTime departureTime) {
        // 업로드 시간 제한 (상품정보를 찾을 시간을 위해서 전날 오후 4시 이전까지만 업로드 가능)
        int departureYear = departureTime.getYear();
        int departureMonth = departureTime.getMonthValue();
        int departureDay = departureTime.getDayOfMonth();

        // 출국 전날 4시
        LocalDateTime uploadDeadLine = LocalDateTime.of(departureYear, departureMonth, departureDay, 16, 0, 0);

        if(LocalDateTime.now().isAfter(uploadDeadLine))
            throw new UploadTimeException("为了确保配送顺畅,图片仅在出境前一天的下午4点之前上传.");
    }

    // 옷 사진 최대 5개 업로드 가능 제한 로직
    private void verifyProductCountFive() {
        if (this.productList.size() >= 5)
        throw new ProductUploadCountException("商品图片最多可以上传5分.");
//            throw new ProductUploadCountException("옷 이미지는 최대 5장 업로드 가능합니다.");
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
