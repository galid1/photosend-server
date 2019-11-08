package com.photosend.photosendserver01.domains.catalog.domain.product;


import com.photosend.photosendserver01.common.config.logging.BaseTimeEntity;
import com.photosend.photosendserver01.domains.user.exception.NotExistProductInformationException;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class ProductEntity extends BaseTimeEntity {
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

    @Builder
    public ProductEntity(@NonNull String productImagePath, ProductLocation productLocation) {
        if(productImagePath == null || productLocation == null) {
            throw new IllegalArgumentException("put All Information for create ProductEntity.");
        }

        this.productImagePath = productImagePath;
        this.productLocation = productLocation;
        this.productState = ProductState.UPLOADED;
    }

    public Long putProductInformation(ProductInformation productInformation) {
        this.productInformation = productInformation;
        this.productState = ProductState.POPULATED;

        return this.pid;
    }

    // 상품 정보가 입력되었는지 검증
    private void verifyExistInformation() {
        if(this.productState != ProductState.POPULATED)
            throw new NotExistProductInformationException("尚未输入商品信息,无法订购");
//            throw new NotExistProductInformationException("아직 상품정보가 입력되지 않아 주문이 불가능합니다.");
    }

}