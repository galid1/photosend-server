package com.photosend.photosendserver01.user.presentation;

import com.photosend.photosendserver01.user.domain.UserInformation;
import com.photosend.photosendserver01.user.domain.exception.UploadTicketException;
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
    public TicketImageUrl uploadTicket(@PathVariable("users-id") Long userId, @RequestParam("file") MultipartFile ticketsFile) {
        return userTicketService.uploadTicketImage(userId, ticketsFile);
    }

    @ExceptionHandler
    public ResponseEntity uploadTicketExceptionHandler(UploadTicketException uploadTicketException) {
        String exceptionJsonBody = "{\"message\" : \"" + uploadTicketException.getMessage() + "\" }";
        return ResponseEntity.badRequest().body(exceptionJsonBody);
    }
}
