package com.photosend.photosendserver01.domains.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByPidAndUserWechatUid(Long pid, String wechatUid);
}
