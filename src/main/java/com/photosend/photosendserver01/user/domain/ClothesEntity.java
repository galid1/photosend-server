package com.photosend.photosendserver01.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "clothes")
public class ClothesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "clothes_id")
    private Long cid;

    // Required Field
    @NonNull
    @Column(name = "image_path")
    private String clothesImagePath;

    // Optional Field
    private String name;
    private Integer price;
    private String brand;
    private Size size;

    @Builder
    public ClothesEntity(@NonNull String clothesImagePath, String name, Integer price, String brand, Size size) {
        this.clothesImagePath = clothesImagePath;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.size = size;
    }
}
