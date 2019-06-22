package com.photosend.photosendserver01.user.presentation;

import com.photosend.photosendserver01.user.domain.exception.TicketException;
import com.photosend.photosendserver01.user.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserTicketController {

    @Autowired
    private UserTicketService userTicketService;

    @GetMapping("/{users-id}/tickets")
    public TicketImageUrl getTicketUrl(@PathVariable("users-id") Long userId) {
        return userTicketService.getUserTicketImageUrl(userId);
    }

    @PostMapping("/{users-id}/tickets")
    public TicketImageUrl uploadTicketImage(@PathVariable("users-id") Long userId, @RequestParam("file") MultipartFile ticketsFile) {
        return userTicketService.uploadTicketImage(userId, ticketsFile);
    }

    // 요청을 5분당 3번만 가능하도록 변경하기
    @PutMapping("/{users-id}/tickets")
    public TicketImageUrl modifyTicketImage(@PathVariable("users-id") Long userId, @RequestParam("file") MultipartFile ticketsFile) {
        return userTicketService.modifyTicketImage(userId, ticketsFile);
    }

    @ExceptionHandler
    public ResponseEntity uploadTicketExceptionHandler(TicketException uploadTicketException) {
        String exceptionJsonBody = "{\"message\" : \"" + uploadTicketException.getMessage() + "\" }";
        return ResponseEntity.badRequest().body(exceptionJsonBody);
    }
}
