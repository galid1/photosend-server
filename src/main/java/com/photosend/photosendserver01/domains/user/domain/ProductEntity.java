package com.photosend.photosendserver01.domains.user.domain;

import com.photosend.photosendserver01.domains.user.domain.exception.NotExistProductInformationException;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long pid;

    // Required Field
    @NonNull
    @Column(name = "image_path")
    private String productImagePath;

    @Embedded
    private ProductLocation productLocation;

    // Optional Field
    @Embedded
    private ProductInformation productInformation;

    @Enumerated(EnumType.STRING)
    private ProductState productState;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public ProductEntity(@NonNull String productImagePath, ProductLocation productLocation, ProductInformation productInformation, UserEntity userEntity) {
        this.productImagePath = productImagePath;
        this.productLocation = productLocation;
        this.productInformation = productInformation;
        this.productState = ProductState.UPLOADED;
        this.user = userEntity;
    }

    public void putProductInformation(ProductInformation productInformation) {
        this.productInformation = productInformation;
        this.productState = ProductState.POPULATED;
    }

    // 상품을 주문 처리
    public void productOrdered() {
        verifyExistInformation();
        this.productState = ProductState.ORDERED;
    }

    // 상품 정보가 입력되었는지 검증
    private void verifyExistInformation() {
        if(this.productState != ProductState.POPULATED)
            throw new NotExistProductInformationException("주문하기전 우선 Product 정보가 입력되어야 합니다.");
    }

}
