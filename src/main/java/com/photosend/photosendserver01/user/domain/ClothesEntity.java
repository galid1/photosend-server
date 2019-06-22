package com.photosend.photosendserver01.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClothesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;

//    @ManyToOne
//    @JoinColumn(name = "uid", insertable = false, updatable = false)
//    private UserEntity entity;

    // Required Field
    @NonNull
    @Column(name = "image_path")
    private String clothImagePath;

    // Optional Field
    private String name;
    private Integer price;
    private String brand;
    private Size size;

    @Builder
    public ClothesEntity(@NonNull String clothImagePath, String name, Integer price, String brand, Size size) {
        this.clothImagePath = clothImagePath;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.size = size;
    }
}
