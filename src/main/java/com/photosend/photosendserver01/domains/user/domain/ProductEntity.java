package com.photosend.photosendserver01.domains.user.domain;

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
    public ProductEntity(@NonNull String productImagePath, ProductLocation productLocation, ProductInformation productInformation, ProductState productState, UserEntity userEntity) {
        this.productImagePath = productImagePath;
        this.productLocation = productLocation;
        this.productInformation = productInformation;
        this.productState = productState;
        this.user = userEntity;
    }

    public void putProductInformation(ProductInformation productInformation) {
        this.productInformation = productInformation;
    }
}
