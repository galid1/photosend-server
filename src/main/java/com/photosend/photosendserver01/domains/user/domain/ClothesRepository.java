package com.photosend.photosendserver01.domains.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesRepository extends JpaRepository<ClothesEntity, Long> {
    ClothesEntity findByCidAndUserWechatUid(Long cid, String wechatUid);
}
