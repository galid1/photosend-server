package com.photosend.photosendserver01.domains.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByPidAndUserWechatUid(Long pid, String wechatUid);

    List<ProductEntity> findByUserWechatUidAndProductStateNot(String wechatUid, ProductState productState);
}
