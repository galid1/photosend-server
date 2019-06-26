package com.photosend.photosendserver01.user.service;

import com.photosend.photosendserver01.user.domain.ClothesEntity;
import com.photosend.photosendserver01.user.domain.ClothesRepository;
import com.photosend.photosendserver01.user.domain.UserEntity;
import com.photosend.photosendserver01.user.domain.UserRepository;
import com.photosend.photosendserver01.user.infra.file.ImageType;
import com.photosend.photosendserver01.user.presentation.Clothes;
import com.photosend.photosendserver01.user.presentation.ClothesImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserClothesService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClothesRepository clothesRepository;
    @Autowired
    UploadFileUtil uploadFileUtil;

    @Transactional
    public List<ClothesImageUrl> uploadClothesImages(Long userId, MultipartFile[] clothesImageFiles) {
        List<ClothesImageUrl> clothesImageUrls = new ArrayList<>();

        // 이미지 업로드
        for (MultipartFile file : clothesImageFiles){
            String uploadPath = uploadFileUtil.makeFileUploadPath(userId, file.getOriginalFilename(), ImageType.CLOTHES);
            uploadFileUtil.uploadFile(uploadPath, file);
            clothesImageUrls.add(ClothesImageUrl.builder().clothesImageUrl(uploadPath).build());
        };

        // 영속화 (유저 엔티티에 이미지 경로 추가)
        addClothesImageUrlsToUser(userId, clothesImageUrls);

        List<ClothesImageUrl> returnClothesImageUrlList = new ArrayList<>();
        userRepository.findById(userId).get().getClothesList().stream().forEach(v -> {
            returnClothesImageUrlList.add(ClothesImageUrl.builder()
                    .cid(v.getCid())
                    .clothesImageUrl(v.getClothesImagePath())
                    .build());
        });
        return returnClothesImageUrlList;
    }

    private void addClothesImageUrlsToUser(Long userId, List<ClothesImageUrl> clothesImageUrls) {
        UserEntity userEntity = userRepository.findById(userId).get(); // ClothesImage url들을 추가할 UserEntity 얻어오기

        clothesImageUrls.stream().forEach(v -> {
            ClothesEntity clothesEntity = v.toEntity();
            clothesRepository.save(clothesEntity); // 새로 생성한 ClothesEntity를 UserEntity의 ClothesList에 저장하기 위해 우선 영속화시킴
            userEntity.putClothesImagePath(clothesEntity);
        });

        userRepository.save(userEntity);
    }

    // clothes의 정보와 이미지를 반환
    public List<Clothes> getClothesList(Long userId) {
        List<ClothesEntity> clothesEntities = userRepository.findById(userId).get().getClothesList();
        List<Clothes> clothesList = new ArrayList<>();
        clothesEntities.stream().forEach(v -> {
            clothesList.add(Clothes.builder()
                                    .clothImagePath(v.getClothesImagePath())
                                    .build());
        });
        return clothesList;
    }

}
