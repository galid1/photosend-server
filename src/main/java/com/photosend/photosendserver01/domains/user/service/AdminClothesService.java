package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.ClothesEntity;
import com.photosend.photosendserver01.domains.user.domain.ClothesInformation;
import com.photosend.photosendserver01.domains.user.domain.ClothesRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundClothesInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminClothesService {
    @Autowired
    private ClothesRepository clothesRepository;

    @Transactional
    public void putFoundClothesInformation(String uid, List<FoundClothesInformation> foundClothesInformationList) {
        foundClothesInformationList.stream().forEach(clothes -> {
            ClothesInformation newClothesInformation = ClothesInformation.builder()
                                                            .brand(clothes.getBrand())
                                                            .name(clothes.getName())
                                                            .price(clothes.getPrice())
                                                            .size(clothes.getSize())
                                                            .build();

            ClothesEntity clothesEntity = clothesRepository.findByCidAndUserWechatUid(clothes.getCid(), uid);
            clothesEntity.putClothesInformation(newClothesInformation);
        });
    }
}
