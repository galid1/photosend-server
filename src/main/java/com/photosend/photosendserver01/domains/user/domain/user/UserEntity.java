package com.photosend.photosendserver01.domains.user.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.photosend.photosendserver01.common.config.logging.BaseTimeEntity;
import com.photosend.photosendserver01.domains.user.exception.ProductUploadCountException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties({"productList"})
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    private String deviceId;

    // Required Field
    @Embedded
    @NonNull
    private UserInformation userInformation;

    @Embedded
    @NonNull
    private Token token;

    @ElementCollection
    @CollectionTable(
        name = "users_product_list",
        joinColumns = @JoinColumn(name = "user_id")
    )
    @JoinColumn(name = "user_id")
    @Column(name = "product_id")
    private List<Long> productList = new ArrayList<>();

    @Builder
    public UserEntity( UserInformation userInformation, String deviceId, @NonNull Token token) {
        setUserInformation(userInformation);
        setDeviceId(deviceId);
        this.token = token;
    }

    private void setUserInformation(UserInformation userInformation) {
        if(userInformation == null) {
            throw new IllegalArgumentException("UserInformation을 입력하세요.");
        }

        if(userInformation.getAge() < 7 || userInformation.getAge() > 110) {
            throw new IllegalArgumentException("사용자의 나이를 올바르게 입력하세요. (7 < age < 110)");
        }

        this.userInformation = userInformation;
    }

    private void setDeviceId(String deviceId) {
        if(deviceId == null || deviceId.length() < 1)
            throw new IllegalArgumentException("올바른 Device Id 값이 아닙니다.");

        this.deviceId = deviceId;
    }

    // 상품 사진의 이미지 경로 추가 메소드
    public void addProduct(long productId) {
        verifyProductCountFive();
        this.productList.add(productId);
    }

    // 옷 사진 최대 5개 업로드 가능 제한 로직
    private void verifyProductCountFive() {
        if(verifyIsAdmin())
            return;

        if (this.productList.size() >= 5)
        throw new ProductUploadCountException("商品图片最多可以上传5分.");
//            throw new ProductUploadCountException("옷 이미지는 최대 5장 업로드 가능합니다.");
    }

    private boolean verifyIsAdmin() {
        if (this.userId == 1l)
            return true;
        return false;
    }
}
