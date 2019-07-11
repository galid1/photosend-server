package com.photosend.photosendserver01.user.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.photosend.photosendserver01.user.domain.exception.TicketException;
import com.photosend.photosendserver01.user.presentation.request_reponse.TicketImageUrl;
import com.photosend.photosendserver01.user.service.UserTicketService;
import com.photosend.photosendserver01.util.ExceptionMessageUtil;
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

    @GetMapping("/{users-id}/tickets")
    public TicketImageUrl getTicketUrl(@PathVariable("users-id") String userId) {
        return userTicketService.getUserTicketImageUrl(userId);
    }

    @PostMapping(value = "/{users-id}/tickets", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TicketImageUrl uploadTicketImage(@PathVariable("users-id") String userId, @RequestParam("file") MultipartFile ticketsFile) {
        return userTicketService.uploadTicketImage(userId, ticketsFile);
    }

    // 요청을 5분당 3번만 가능하도록 변경하기
    @PutMapping("/{users-id}/tickets")
    public TicketImageUrl modifyTicketImage(@PathVariable("users-id") String userId, @RequestParam("file") MultipartFile ticketsFile) {
        return userTicketService.modifyTicketImage(userId, ticketsFile);
    }

    @ExceptionHandler
    public ResponseEntity uploadTicketExceptionHandler(TicketException uploadTicketException) throws JsonProcessingException {
        String exceptionJsonBody = exceptionMessageUtil.createExceptionMessage(uploadTicketException.getMessage());
        return ResponseEntity.badRequest().body(exceptionJsonBody);
    }
}
