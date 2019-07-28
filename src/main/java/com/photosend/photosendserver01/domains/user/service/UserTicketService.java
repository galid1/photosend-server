package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.domain.exception.UserNotFoundException;
import com.photosend.photosendserver01.domains.user.infra.file.ImageType;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.TicketImageUrl;
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
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
        userEntity.putTicketsImagePath(imageUrl);

        // storage에 file upload
        fileUtil.uploadFile(imageUrl, ticketImageFile);

        return new TicketImageUrl(imageUrl);
    }

    @Transactional
    public TicketImageUrl modifyTicketImage(String userId, MultipartFile ticketImageFile) {
        String imageUrl = fileUtil.makeFileUploadPath(userId, ticketImageFile.getOriginalFilename(), ImageType.TICKET);

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
        userEntity.modifyTicketsImagePath(imageUrl);

        fileUtil.uploadFile(imageUrl, ticketImageFile);
        return TicketImageUrl.builder().ticketImageUrl(imageUrl).build();
    }

}
