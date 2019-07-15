package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.ClothesEntity;
import com.photosend.photosendserver01.domains.user.domain.ClothesRepository;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.domain.exception.ClothesException;
import com.photosend.photosendserver01.domains.user.domain.exception.FileDeleteException;
import com.photosend.photosendserver01.domains.user.infra.file.ImageType;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.Clothes;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.ClothesImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserClothesService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClothesRepository clothesRepository;
    @Autowired
    private FileUtil fileUtil;

    @Transactional
    public List<ClothesImageUrl> uploadClothesImages(String userId, MultipartFile[] clothesImageFiles) {
        List<ClothesImageUrl> clothesImageUrls = new ArrayList<>();

        // 스토리지에 이미지 업로드
        uploadImageToStorage(userId, clothesImageFiles, clothesImageUrls);

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

    private void uploadImageToStorage(String userId, MultipartFile[] clothesImageFiles, List<ClothesImageUrl> clothesImageUrls) {
        for (MultipartFile file : clothesImageFiles){
            String uploadPath = fileUtil.makeFileUploadPath(userId, file.getOriginalFilename(), ImageType.CLOTHES);
            fileUtil.uploadFile(uploadPath, file);
            clothesImageUrls.add(ClothesImageUrl.builder().clothesImageUrl(uploadPath).build());
        }
    }

    private void addClothesImageUrlsToUser(String userId, List<ClothesImageUrl> clothesImageUrls) {
        UserEntity userEntity = userRepository.findById(userId).get(); // ClothesImage url들을 추가할 UserEntity 얻어오기

        clothesImageUrls.stream().forEach(v -> {
            ClothesEntity clothesEntity = v.toEntity();
            clothesRepository.save(clothesEntity); // 새로 생성한 ClothesEntity를 UserEntity의 ClothesList에 저장하기 위해 우선 영속화시킴
            userEntity.putClothesImagePath(clothesEntity);
        });

        userRepository.save(userEntity);
    }

    // clothes의 정보와 이미지를 반환
    public List<Clothes> getClothesList(String userId) {
        List<ClothesEntity> clothesEntities = userRepository.findById(userId).get().getClothesList();
        List<Clothes> clothesList = new ArrayList<>();
        clothesEntities.stream().forEach(v -> {
            clothesList.add(Clothes.builder()
                                    .clothImagePath(v.getClothesImagePath())
                                    .build());
        });
        return clothesList;
    }

    // ClothesImage Delete Method
    @Transactional
    public void deleteClothesImage(String userId, Long clothesId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        String clothesImagePath = userEntity.getClothesList().get(0).getClothesImagePath();

        // 스토리지에서 이미지 제거
        deleteImageFromStorage(clothesImagePath);

        // UserEntity가 가지는 clothesEntity 리스트에서 제거
        int clothesIndex = findClothesIndexById(userId, clothesId);

        if(clothesIndex == -1)
            throw new ClothesException("해당 id의 ClothesEntity가 존재하지 않습니다.");

        userEntity.deleteClothes(clothesIndex);
        userRepository.save(userEntity);
    }

    private void deleteImageFromStorage(String clothesImagePath) {
        // local file delete
        File file = new File(clothesImagePath);
        if(!file.exists())
            throw new FileDeleteException("경로에 파일이 존재하지 않습니다.");
        file.delete();
    }

    private int findClothesIndexById(String uid, Long cid) {
        UserEntity userEntity = userRepository.findById(uid).get();
        int index = 0;
        ClothesEntity clothesEntity = null;

        for (ClothesEntity entity : userEntity.getClothesList()) {
            if(entity.getCid() == cid) {
                clothesEntity = entity;
                break;
            }
            index ++;
        }

        return clothesEntity != null ? index : -1;
    }

}
