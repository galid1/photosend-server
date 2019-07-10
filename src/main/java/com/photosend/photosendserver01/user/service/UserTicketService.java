package com.photosend.photosendserver01.user.service;

import com.photosend.photosendserver01.user.domain.UserEntity;
import com.photosend.photosendserver01.user.domain.UserRepository;
import com.photosend.photosendserver01.user.infra.file.ImageType;
import com.photosend.photosendserver01.user.presentation.request_reponse.TicketImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserTicketService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUtil fileUtil;

    public TicketImageUrl getUserTicketImageUrl(String userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return new TicketImageUrl(userEntity.get().getTicket().getTicketImagePath());
    }

    @Transactional
    public TicketImageUrl uploadTicketImage(String userId, MultipartFile ticketImageFile) {
        // uploadPath 얻어오기
        String imageUrl = fileUtil.makeFileUploadPath(userId, ticketImageFile.getOriginalFilename(), ImageType.TICKET);

        // userentity에 tickets imageUrl 추가
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        userEntity.get().putTicketsImagePath(imageUrl);

        // storage에 file upload
        fileUtil.uploadFile(imageUrl, ticketImageFile);

        return new TicketImageUrl(imageUrl);
    }

    @Transactional
    public TicketImageUrl modifyTicketImage(String userId, MultipartFile ticketImageFile) {
        String imageUrl = fileUtil.makeFileUploadPath(userId, ticketImageFile.getOriginalFilename(), ImageType.TICKET);

        Optional<UserEntity> userEntity = userRepository.findById(userId);
        userEntity.get().modifyTicketsImagePath(imageUrl);

        fileUtil.uploadFile(imageUrl, ticketImageFile);
        return TicketImageUrl.builder().ticketImageUrl(imageUrl).build();
    }

}
