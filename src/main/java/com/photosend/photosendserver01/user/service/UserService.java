package com.photosend.photosendserver01.user.service;

import com.photosend.photosendserver01.user.domain.ClothesEntity;
import com.photosend.photosendserver01.user.domain.UserEntity;
import com.photosend.photosendserver01.user.domain.UserInformation;
import com.photosend.photosendserver01.user.domain.UserRepository;
import com.photosend.photosendserver01.user.presentation.TicketImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UploadFileUtil uploadFileUtil;

    @Transactional
    public Long registerUser(UserInformation userInformation) {
        UserEntity entity = UserEntity.builder()
                .userInformation(userInformation)
                .build();
        return userRepository.save(entity).getUid();
    }

    public TicketImageUrl getUserTicketImageUrl(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return new TicketImageUrl(userEntity.get().getTicket().getTicketImagePath());
    }

    @Transactional
    public TicketImageUrl uploadTicketImage(Long userId, MultipartFile ticketImageFile) {
        // uploadPath 얻어오기
        String imageUrl = uploadFileUtil.makeFileUploadPath(userId, ticketImageFile.getOriginalFilename());

        // userentity에 tickets imageUrl 추가
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        userEntity.putTicketsImagePath(imageUrl);

        // storage에 file upload
        uploadFileUtil.uploadFile(imageUrl, ticketImageFile);

        return new TicketImageUrl(imageUrl);
    }

    @Transactional
    public Long uploadClothImage(Long userId, ClothesEntity cloth) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);

        if (userEntity == null)
            throw new IllegalArgumentException("없는 회원입니다.");

        userEntity.uploadCloth(cloth);
        return Long.valueOf(userEntity.getLastUploadClothIndex());
    }

    public Long putClothInfo(Long clothId) {
        return 0l;
    }

}
