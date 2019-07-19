package com.photosend.photosendserver01.domains.user.domain;

import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundClothesInformation;
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

    @Embedded
    private ClothesLocation clothesLocation;

    // Optional Field
    @Embedded
    private ClothesInformation clothesInformation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public ClothesEntity(@NonNull String clothesImagePath, ClothesLocation clothesLocation, ClothesInformation clothesInformation, UserEntity userEntity) {
        this.clothesImagePath = clothesImagePath;
        this.clothesLocation = clothesLocation;
        this.clothesInformation = clothesInformation;
        this.user = userEntity;
    }

    public void putClothesInformation(ClothesInformation clothesInformation) {
        this.clothesInformation = clothesInformation;
    }
}
