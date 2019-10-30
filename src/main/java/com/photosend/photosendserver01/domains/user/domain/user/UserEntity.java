package com.photosend.photosendserver01.domains.user.domain.user;

import com.photosend.photosendserver01.common.config.logging.BaseTimeEntity;
import com.photosend.photosendserver01.domains.user.exception.*;
import com.photosend.photosendserver01.domains.user.domain.product.ProductEntity;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
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

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private List<ProductEntity> productList = new ArrayList<>();

    @Builder
    public UserEntity(@NonNull String weChatOpenId, @NonNull UserInformation userInformation, @NonNull Token token) {
        this.weChatOpenId = weChatOpenId;
        setUserInformation(userInformation);
        this.token = token;
    }

    private void setUserInformation(UserInformation userInformation) {
        if(userInformation == null) {
            throw new IllegalArgumentException("UserInformation을 입력하세요.");
        }
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
        LocalDate now = LocalDate.now();
        LocalDate departure = LocalDate.of(departureTime.getYear(), departureTime.getMonth(), departureTime.getDayOfMonth());

        // 가입 시간 제한 최소 출국 하루 전
        if(Duration.between(now.atStartOfDay(), departure.atStartOfDay()).toDays() < 1) {
            throw new DepartureTimeException("为了方便配送,至少在出境前一天可以加入."); // "원활한 배송을 위해 최소 출국하루전에만 사용이 가능합니다"
        }

        // 가입 시간 제한 (배송을 위해서 오전10시에서 오후8시 사이에 출국하는 사람에 한해서만 사용가능)
        int departureHour = departureTime.getHour();
        if(departureHour < registerMinTime || departureHour > registerMaxTime)
            throw new DepartureTimeException("为了确保顺畅地配送,仅允许上午10点至下午8点出境的用户使用."); // "원활한 배송을 위해 오전 10시에서 오후 8시 사이에 출국하는 사용자만 이용 가능합니다."

    }

    // 상품 사진의 이미지 경로 추가 메소드
    public void addProduct(ProductEntity productEntity) {
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
}
