package com.photosend.photosendserver01.domains.user.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.photosend.photosendserver01.domains.user.domain.exception.TicketException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.TicketImageUrl;
import com.photosend.photosendserver01.domains.user.service.UserTicketService;
import com.photosend.photosendserver01.common.util.exception.ExceptionMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserTicketController {

    @Autowired
    private UserTicketService userTicketService;
    @Autowired
    private ExceptionMessageUtil exceptionMessageUtil;

    @GetMapping("/{usersId}/tickets")
    public TicketImageUrl getTicketUrl(@PathVariable("usersId") Long userId) {
        return userTicketService.getUserTicketImageUrl(userId);
    }

    @PostMapping(value = "/{usersId}/tickets", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TicketImageUrl uploadTicketImage(@PathVariable("usersId") Long userId, @RequestParam("file") MultipartFile ticketsFile) {
        return userTicketService.uploadTicketImage(userId, ticketsFile);
    }

    // 요청을 5분당 3번만 가능하도록 변경하기
    @PutMapping("/{usersId}/tickets")
    public TicketImageUrl modifyTicketImage(@PathVariable("usersId") Long userId, @RequestParam("file") MultipartFile ticketsFile) {
        return userTicketService.modifyTicketImage(userId, ticketsFile);
    }

    @ExceptionHandler
    public ResponseEntity uploadTicketExceptionHandler(TicketException uploadTicketException) throws JsonProcessingException {
        String exceptionJsonBody = exceptionMessageUtil.createExceptionMessage(uploadTicketException.getMessage());
        return ResponseEntity.badRequest().body(exceptionJsonBody);
    }
}
