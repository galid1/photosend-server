package com.photosend.photosendserver01.domains.user.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByWeChatOpenId(String weChatOpenId);
}
