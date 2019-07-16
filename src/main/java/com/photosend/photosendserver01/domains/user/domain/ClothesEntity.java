package com.photosend.photosendserver01.domains.user.domain;

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
    @Embedded
    private ClothesInformation clothesInformation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public ClothesEntity(@NonNull String clothesImagePath, ClothesInformation clothesInformation, UserEntity userEntity) {
        this.clothesImagePath = clothesImagePath;
        this.clothesInformation = clothesInformation;
        this.user = userEntity;
    }
}
