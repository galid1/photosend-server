package com.photosend.photosendserver01.domains.catalog.domain.product;


import com.photosend.photosendserver01.common.config.logging.BaseTimeEntity;
import com.photosend.photosendserver01.domains.user.exception.NotExistProductInformationException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Embedded
    private ProductImageInformation productImageInformation;

    // Optional Field
    @Embedded
    private ProductInformation productInformation;

    @Enumerated(EnumType.STRING)
    private ProductState productState;

    @Builder
    public ProductEntity(ProductImageInformation productImageInformation) {
        this.setProductImageInformation(productImageInformation);
        this.productState = ProductState.UPLOADED;
    }

    private void setProductImageInformation(ProductImageInformation productImageInformation) {
        if(productImageInformation.getProductImagePath() == null
                || productImageInformation.getProductLocation() == null) {
            throw new IllegalArgumentException("put All Information for create ProductEntity.");
        }
        this.productImageInformation = productImageInformation;
    }

    public void putProductInformation(ProductInformation productInformation) {
        this.productInformation = productInformation;
        this.productState = ProductState.POPULATED;
    }

    // 상품 정보가 입력되었는지 검증
    private void verifyExistInformation() {
        if(this.productState != ProductState.POPULATED)
            throw new NotExistProductInformationException("尚未输入商品信息,无法订购");
//            throw new NotExistProductInformationException("아직 상품정보가 입력되지 않아 주문이 불가능합니다.");
    }

}
